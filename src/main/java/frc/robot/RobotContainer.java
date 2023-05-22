package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.Teleop.defaultSwerve;
import frc.robot.Subsystems.Drivetrain.Swerve;

public class RobotContainer {
  private final XboxController xDrive = new XboxController(0);
  private final XboxController xManip = new XboxController(1);
  private final Joystick keyboard = new Joystick(5);

  

  private final Swerve mSwerve = Swerve.getInstance();

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
      new defaultSwerve(
        xDrive::getLeftX,
        xDrive::getLeftY,
        xDrive::getRightX,
        xDrive::getRightBumper
    ));

    new JoystickButton(xDrive, XboxController.Button.kBack.value).onTrue(new InstantCommand(mSwerve::zeroGyro, mSwerve)).debounce(Constants.OperatorConstants.Debounce.kButton);
    new JoystickButton(xDrive, XboxController.Button.kB.value).onTrue(new InstantCommand(mSwerve::lock, mSwerve)).debounce(Constants.OperatorConstants.Debounce.kButton);
  }

  private void configureSimBindings() {
    mSwerve.setDefaultCommand(
      new defaultSwerve(
        () -> keyboard.getRawAxis(1),
        () -> keyboard.getRawAxis(0),
        () -> keyboard.getRawAxis(2),
        () -> keyboard.getRawButton(1)
    ));

    new JoystickButton(keyboard, 2).onTrue(new InstantCommand(() -> mSwerve.robotCentricSup = !mSwerve.robotCentricSup));
    new JoystickButton(keyboard, 3).onTrue(new InstantCommand(mSwerve::zeroGyro, mSwerve)).debounce(Constants.OperatorConstants.Debounce.kButton);
    new JoystickButton(keyboard, 4).onTrue(new InstantCommand(mSwerve::lock, mSwerve)).debounce(Constants.OperatorConstants.Debounce.kButton);

  }
}
