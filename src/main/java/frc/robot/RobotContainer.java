package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.CraneCommands.Arm.ProfiledChangeSetPoint;
import frc.robot.Commands.CraneCommands.Hand.SetGrip;
import frc.robot.Commands.CraneCommands.Lights.LightCMD;
import frc.robot.Commands.CraneCommands.Lights.LightReqCMD;
import frc.robot.Commands.Swerve.GetToPosition;
import frc.robot.Commands.Swerve.balance;
import frc.robot.Commands.Teleop.FineAdjust;
import frc.robot.Commands.Teleop.TeleopSwerve;
import frc.robot.Custom.Utils;
import frc.robot.Subsystems.CraneAssembly.Arm;
import frc.robot.Subsystems.CraneAssembly.Hand;
import frc.robot.Subsystems.CraneAssembly.Lighting;
import frc.robot.Subsystems.Drivetrain.Swerve;
import frc.robot.Subsystems.Vision.Vision;
import frc.robot.Subsystems.Vision.Vision.LimelightMeasurement;

public class RobotContainer {
  private final XboxController xDrive = new XboxController(0);
  public final static XboxController xManip = new XboxController(1);
  private final Joystick keyboard = new Joystick(5);

  private final Swerve mSwerve = Swerve.getInstance();
  private final Arm mArm = Arm.getInstance();
  private final Hand mHand = Hand.getInstance();
  private final Vision mVision = Vision.getInstance();

  public RobotContainer() {
    if (Robot.isSimulation()) {
      configureSimBindings();
    } else {
      configureBindings();
    }
  }

  private void configureBindings() {

    /* Default Commands */

    mSwerve.setDefaultCommand(
        new TeleopSwerve(
            xDrive::getLeftX,
            xDrive::getLeftY,
            xDrive::getRightX,
            xDrive::getRightBumper));

    new JoystickButton(xDrive, XboxController.Button.kBack.value).onTrue(new InstantCommand(mSwerve::zeroGyro, mSwerve))
        .debounce(Constants.OperatorConstants.Debounce.kButton);
    new JoystickButton(xDrive, XboxController.Button.kB.value).onTrue(new InstantCommand(mSwerve::lock, mSwerve))
        .debounce(Constants.OperatorConstants.Debounce.kButton);
    new JoystickButton(xManip, XboxController.Button.kA.value).onTrue(new PrintCommand("I hate yellow"));

    mArm.setDefaultCommand(
        new FineAdjust(
            () -> Utils.customDeadzone(-xManip.getLeftY()),
            () -> Utils.customDeadzone(-xManip.getRightY())));

    // new JoystickButton(mDriverController,
    // XboxController.Button.kY.value).whileTrue(new Flatten(0.3));
    new JoystickButton(xDrive, XboxController.Button.kX.value).whileTrue(new balance());
    new JoystickButton(xDrive, XboxController.Button.kBack.value)
        .toggleOnTrue(new InstantCommand(mSwerve::zeroGyro));
    new JoystickButton(xDrive, XboxController.Button.kY.value)
        .toggleOnTrue(new InstantCommand(mSwerve::lock));

    new JoystickButton(xDrive, XboxController.Button.kRightBumper.value).onTrue(
        new InstantCommand(() -> {
          Optional<LimelightMeasurement> leftMeasurement = mVision.getNewLeftMeasurement();
          Optional<LimelightMeasurement> rightMeasurement = mVision.getNewRightMeasurement();
          if (leftMeasurement.isPresent()) {
            mSwerve.resetOdometry(leftMeasurement.get().mPose);
          } else if (rightMeasurement.isPresent()) {
            mSwerve.resetOdometry(rightMeasurement.get().mPose);
          }
        }));

    new JoystickButton(xManip, XboxController.Button.kLeftBumper.value).whileTrue(new SetGrip());
    new JoystickButton(xManip, XboxController.Button.kY.value).onTrue(
        ProfiledChangeSetPoint.createWithTimeout(() -> mHand.holdingCone ? Constants.Arm.SetPoint.coneHighPosition
            : Constants.Arm.SetPoint.cubeHighPosition));
    new JoystickButton(xManip, XboxController.Button.kB.value).onTrue(
        ProfiledChangeSetPoint.createWithTimeout(() -> mHand.holdingCone ? Constants.Arm.SetPoint.coneMediumPosition
            : Constants.Arm.SetPoint.cubeMediumPosition));
    new JoystickButton(xManip, XboxController.Button.kA.value)
        .onTrue(ProfiledChangeSetPoint.createWithTimeout(() -> Constants.Arm.SetPoint.lowPosition));
    new JoystickButton(xManip, XboxController.Button.kX.value)
        .onTrue(ProfiledChangeSetPoint.createWithTimeout(() -> Constants.Arm.SetPoint.generalIntakePosition));
    new JoystickButton(xManip, XboxController.Button.kLeftStick.value)
        .onTrue(ProfiledChangeSetPoint.createWithTimeout(() -> Constants.Arm.SetPoint.stowPosition));
    new JoystickButton(xManip, XboxController.Button.kRightStick.value)
        .onTrue(ProfiledChangeSetPoint.createWithTimeout(() -> Constants.Arm.SetPoint.loadingZonePosition));
    new JoystickButton(xManip, XboxController.Button.kBack.value)
        .onTrue(ProfiledChangeSetPoint.createWithTimeout(() -> Constants.Arm.SetPoint.startPosition));

    // Trigger leftTrigger = new JoystickButton(xManip,
    // XboxController.Axis.kLeftTrigger.value);
    // new Trigger(() -> xManip.getLeftTriggerAxis() > 0.5)
    // .whileTrue(new InstantCommand(() -> s_Arm.resetLash())
    // .alongWith(new LoggyPrintCommand(leftTrigger))
    // );

    // new POVButton(xDrive, 90).onTrue(charlieAutoGrab.getCommand());

    // new POVButton(xManip, 270).onTrue(new ToggleHolding().andThen(new
    // WaitCommand((2))).andThen(()->xManip.setRumble(RumbleType.kBothRumble,
    // 0)).handleInterrupt(()->xManip.setRumble(RumbleType.kBothRumble,
    // 0)));

    new POVButton(xManip, 90).onTrue(new LightReqCMD()).debounce(Constants.OperatorConstants.Debounce.kDPad);
    new Trigger(() -> Lighting.timer.hasElapsed(Constants.Lights.blinkTime))
        .onTrue(new InstantCommand(() -> new LightCMD(Lighting.PWMVal).schedule()));

    // new POVButton(xManip, 270).onTrue(new SetHolding(false).andThen(new
    // WaitCommand((2))).andThen(()->xManip.setRumble(RumbleType.kBothRumble,
    // 0)).handleInterrupt(()->xManip.setRumble(RumbleType.kBothRumble,
    // 0)));
    new POVButton(xManip, 180)
        .onTrue(ProfiledChangeSetPoint.createWithTimeout(() -> Constants.Arm.SetPoint.upIntakePosition));

    new POVButton(xDrive, 270).onTrue(new GetToPosition());
    // new POVButton(xDrive, 90).onTrue(charlieAutoGrab.getCommand());
  }

  private void configureSimBindings() {
    mSwerve.setDefaultCommand(
        new TeleopSwerve(
            () -> keyboard.getRawAxis(1),
            () -> keyboard.getRawAxis(0),
            () -> keyboard.getRawAxis(2),
            () -> keyboard.getRawButton(1)
    ));
    
    mArm.setDefaultCommand(
        new FineAdjust(
                () -> keyboard.getRawAxis(3),
                () -> keyboard.getRawAxis(4)
        ));

    new JoystickButton(keyboard, 2).onTrue(new InstantCommand(() -> mSwerve.robotCentricSup = !mSwerve.robotCentricSup))
        .debounce(Constants.OperatorConstants.Debounce.kButton);
    new JoystickButton(keyboard, 3).onTrue(new InstantCommand(mSwerve::zeroGyro, mSwerve))
        .debounce(Constants.OperatorConstants.Debounce.kButton);
    new JoystickButton(keyboard, 4).onTrue(new InstantCommand(mSwerve::lock, mSwerve))
        .debounce(Constants.OperatorConstants.Debounce.kButton);

    new JoystickButton(keyboard, 5)
        .onTrue(ProfiledChangeSetPoint.createWithTimeout(() -> Constants.Arm.SetPoint.coneHighPosition))
        .debounce(Constants.OperatorConstants.Debounce.kButton);
    new JoystickButton(keyboard, 6)
        .onTrue(ProfiledChangeSetPoint.createWithTimeout(() -> Constants.Arm.SetPoint.coneMediumPosition))
        .debounce(Constants.OperatorConstants.Debounce.kButton);
    new JoystickButton(keyboard, 7)
        .onTrue(ProfiledChangeSetPoint.createWithTimeout(() -> Constants.Arm.SetPoint.generalIntakePosition))
        .debounce(Constants.OperatorConstants.Debounce.kButton);

  }
}
