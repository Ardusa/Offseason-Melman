// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Commands.Teleop.defaultSwerve;
import frc.robot.Subsystems.Drivetrain.Swerve;
import frc.robot.Subsystems.Vision.Vision;

public class RobotContainer {
  private final XboxController xDrive = new XboxController(0);
  // private final XboxController xManip = new XboxController(1);

  private final Swerve mSwerve = Swerve.getInstance();
  private final Vision mVision = Vision.getInstance();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    mSwerve.setDefaultCommand(
      new defaultSwerve(
        () -> -xDrive.getLeftX(),
        () -> -xDrive.getLeftY(),
        () -> -xDrive.getRightX(),
        () -> xDrive.getStartButton(),
        () -> xDrive.getRightBumper()
    ));
    
    

  }
}
