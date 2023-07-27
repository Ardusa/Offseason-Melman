// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.tagAction;
import frc.robot.Commands.Drive.arcadeDrive;
import frc.robot.Subsystems.Drivetrain;

//import frc.robot.subsystems.Driver;
//import frc.robot.subsystems.Manip;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain mDrivetrain = Drivetrain.getInstance();

  // private final Driver mDriver = Driver.getInstance();

  // Triggers
  private final XboxController xDrive = new XboxController(0);
  // private final XboxController xManip = new XboxController(1);

  /**
   * private final JoystickButton manipRT = new JoystickButton(xManip,
   * XboxController.Axis.kRightTrigger.value);
   * private final JoystickButton manipLT = new JoystickButton(xManip,
   * XboxController.Axis.kLeftTrigger.value);
   * private final JoystickButton manipRB = new JoystickButton(xManip,
   * XboxController.Button.kRightBumper.value);
   * private final JoystickButton manipLB = new JoystickButton(xManip,
   * XboxController.Button.kLeftBumper.value);
   * private final JoystickButton manipRX = new JoystickButton(xManip,
   * XboxController.Axis.kRightX.value);
   * private final JoystickButton manipRY = new JoystickButton(xManip,
   * XboxController.Axis.kRightY.value);
   */

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureBindings() {
    mDrivetrain.setDefaultCommand(new arcadeDrive(
        () -> xDrive.getLeftY(),
        () -> xDrive.getRightX()));

    new JoystickButton(xDrive, XboxController.Button.kB.value).whileTrue(new tagAction(
        SmartDashboard.getNumber("limelight-driver/tid", 0), SmartDashboard.getNumber("limelight-driver/tx", 0)));
  }


  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}

/**
 * Use this to pass the autonomous command to the main {@link Robot} class.
 *
 * @return the command to run in autonomous
 */

/**
 * RepeatCommand.whileTrue(new ElevatorCMD(
 * () -> mManip.getRTrigger(),
 * () -> mManip.getLTrigger()
 * // () -> mManip.getRX(),
 * // () -> mManip.getRY()
 * )
 * );
 * 
 * (manipRT.or(manipLT).or(manipRX).or(manipRY).whileTrue(new ElevatorCMD(
 * () -> mManip.getRTrigger(),
 * () -> mManip.getLTrigger()
 * // () -> mManip.getRX(),
 * // () -> mManip.getRY()
 * )
 * )
 * );
 * 
 * RepeatCommand.manipRB.or(manipLB).whileTrue(new IntakeCMD(
 * () -> mManip.getRBumper()?1:0,
 * () -> mManip.getLBumper()?1:0
 * )
 * );
 */