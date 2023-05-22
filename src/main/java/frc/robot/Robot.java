// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.server.PathPlannerServer;
import com.revrobotics.REVPhysicsSim;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Auton.chooser;
import frc.robot.Auton.pathPlannerChooser;
import frc.robot.Custom.CTREConfigs;
import frc.robot.Custom.LoggyThings.LoggyThingManager;
import frc.robot.Subsystems.Drivetrain.Swerve;

public class Robot extends TimedRobot {
    public chooser chooser;
    public pathPlannerChooser pathPlanner;
    private Command m_autonomousCommand;
    public SendableChooser<String> autonChooser;
    public static CTREConfigs ctreConfigs;

    @Override
    public void robotInit() {
        PathPlannerServer.startServer(Constants.softwareConstants.PathPlannerLibPort);
        ctreConfigs = new CTREConfigs();
        new RobotContainer();

        DriverStation.silenceJoystickConnectionWarning(true);

        autonChooser = new SendableChooser<>();
        autonChooser.setDefaultOption("Nothing", "null");
        // autonChooser.addOption("Path 1", "Path1.json");
        // autonChooser.addOption("Coop No Bump", "CoopertitionNoBump.json");
        autonChooser.addOption("Place and Charge Path", "1pieceAndCharge");
        SmartDashboard.putData("Autonomous Command", autonChooser);

        CommandScheduler.getInstance().onCommandInitialize((Command c) -> {DataLogManager.log("INITIALIZED: " + c.getName());});
        CommandScheduler.getInstance().onCommandFinish((Command c) -> {DataLogManager.log("FINISHED: " + c.getName());});
        CommandScheduler.getInstance().onCommandInterrupt((Command c) -> {DataLogManager.log("INTERUPTED: " + c.getName());});
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        LoggyThingManager.getInstance().periodic();
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {}

    @Override
    public void autonomousInit() {
        // chooser = new chooser(autonChooser.getSelected());
        // m_autonomousCommand = chooser.generateTrajectory();

        pathPlanner = new pathPlannerChooser(autonChooser.getSelected());
        m_autonomousCommand = pathPlanner.generateTrajectory();

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }

        new Trigger(() -> pathPlanner.autonFinished).onTrue(new InstantCommand(() -> Swerve.getInstance().lock(), Swerve.getInstance()));
            // .onTrue(new balance().repeatedly().until(() -> Math.abs(Swerve.getInstance().getPitchAngle()) <= 0.05));
        // System.out.println(pathPlanner.autonPoses());
    }

    @Override
    public void autonomousPeriodic() {

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
    public void testPeriodic() {}

    @Override
    public void testExit() {}

    @Override
    public void simulationInit() {}
    
    @Override
    public void simulationPeriodic() {
        REVPhysicsSim.getInstance().run();
    }

}
