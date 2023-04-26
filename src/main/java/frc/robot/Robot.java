// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Auton.chooser;
import frc.robot.Commands.Teleop.defaultSwerve;
import frc.robot.Custom.CTREConfigs;

public class Robot extends TimedRobot {
  public chooser chooser;
  private Command m_autonomousCommand;
  public SendableChooser<String> autonChooser = new SendableChooser<String>();
  // private RobotContainer m_robotContainer;
  public static CTREConfigs ctreConfigs;

  @Override
  public void robotInit() {
    ctreConfigs = new CTREConfigs();
    new RobotContainer();
    autonChooser.setDefaultOption("Nothing", "null");
    autonChooser.addOption("Path 1", "Path1.json");
    SmartDashboard.putData("Autonomous Command", autonChooser);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    // m_autonomousCommand = Commands.print(autonChooser.getSelected());
    // m_autonomousCommand.schedule();
    chooser = new chooser(autonChooser.getSelected());
    m_autonomousCommand = chooser.getTrajectory();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    System.out.println(m_autonomousCommand.getName());
    System.out.println(chooser.trajectory.getTotalTimeSeconds());
    // System.out.println("Module 0 Speed: " + Swerve.getInstance().mSwerveMods[0].getState().speedMetersPerSecond);
  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
    new defaultSwerve(() -> 0.5, () -> 0, () -> 0, () -> false, () -> false).repeatedly();
  }

  @Override
  public void testExit() {}

  @Override
  public void simulationInit() {
    DriverStation.silenceJoystickConnectionWarning(true);
  }
}
