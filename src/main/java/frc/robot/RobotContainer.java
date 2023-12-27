
package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.autos.autoFromPath;
import frc.robot.commands.Lights.LightCMD;
import frc.robot.commands.Lights.LightReqCMD;
import frc.robot.commands.Swerve.GetToPosition;
import frc.robot.commands.Swerve.TeleopSwerve;
import frc.robot.commands.Swerve.balance;
import frc.robot.subsystems.*;
import frc.robot.Vision.LimelightMeasurement;

public class RobotContainer {
	/* Controllers */
	public static final XboxController mDriverController = new XboxController(0);
	public static final XboxController mManipController = new XboxController(1);

	/* Subsystems */
	private final Swerve mSwerve = Swerve.getInstance();
	private final Vision mVision = Vision.getInstance();

	public RobotContainer() {
		configureButtonBindings();
	}

	private void configureButtonBindings() {
		mSwerve.setDefaultCommand(
			new TeleopSwerve(
				() -> -mDriverController.getLeftY(),
				() -> -mDriverController.getLeftX(),
				() -> -mDriverController.getRightX(),
				() ->  mDriverController.getAButton(),
				() ->  mDriverController.getLeftBumper()
			)
		);

		// new JoystickButton(mDriverController, XboxController.Button.kY.value).whileTrue(new Flatten(0.3));
		new JoystickButton(mDriverController, XboxController.Button.kX.value).whileTrue(new balance());
		new JoystickButton(mDriverController, XboxController.Button.kBack.value).toggleOnTrue(new InstantCommand(() -> mSwerve.zeroGyro()));
		new JoystickButton(mDriverController, XboxController.Button.kY.value).toggleOnTrue(new InstantCommand(() -> mSwerve.lockPosition()));
		new JoystickButton(mDriverController, XboxController.Button.kRightBumper.value).onTrue(
			new InstantCommand(() -> {
				Optional<LimelightMeasurement> leftMeasurement = mVision.getNewLeftMeasurement();
				Optional<LimelightMeasurement> rightMeasurement = mVision.getNewRightMeasurement();
				if (leftMeasurement.isPresent()) {mSwerve.resetOdometry(leftMeasurement.get().mPose);}
				else if(rightMeasurement.isPresent()) {mSwerve.resetOdometry(rightMeasurement.get().mPose);}})
		);

		new POVButton(mManipController, 90).onTrue(new LightReqCMD());
		new Trigger(() -> Lighting.timer.hasElapsed(Constants.Lights.blinkTime))
			.onTrue(new InstantCommand(() -> new LightCMD(Lighting.PWMVal).schedule()));

		new POVButton(mDriverController, 270).onTrue(new GetToPosition());
	}

	public Command getAutonomousCommand() {
		return new autoFromPath();
	}
}
