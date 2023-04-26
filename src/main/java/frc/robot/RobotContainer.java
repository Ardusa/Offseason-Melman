package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.Teleop.defaultSwerve;
import frc.robot.Subsystems.Drivetrain.Swerve;

public class RobotContainer {
  private final XboxController xDrive = new XboxController(0);
  private final XboxController xManip = new XboxController(1);

  private final Swerve mSwerve = Swerve.getInstance();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    /* Default Commands */
    mSwerve.setDefaultCommand(
      new defaultSwerve(
        () -> -xDrive.getLeftX(),
        () -> -xDrive.getLeftY(),
        () -> -xDrive.getRightX(),
        xDrive::getStartButton,
        xDrive::getRightBumper
    ));

    new JoystickButton(xDrive, XboxController.Button.kBack.value).onTrue(new InstantCommand(mSwerve::zeroGyro, mSwerve));
    new JoystickButton(xDrive, XboxController.Button.kB.value).onTrue(new InstantCommand(mSwerve::lockPosition, mSwerve));
  }
}
