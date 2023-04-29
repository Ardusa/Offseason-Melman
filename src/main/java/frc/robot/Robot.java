// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Custom.pathplanner.lib.server.PathPlannerServer;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggedPowerDistribution;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.Auton.chooser;
import frc.robot.Auton.pathPlannerChooser;
import frc.robot.Commands.Swerve.balance;
import frc.robot.Custom.CTREConfigs;
import frc.robot.Custom.LoggyThings.LoggyThingManager;
import frc.robot.Subsystems.Drivetrain.Swerve;

public class Robot extends LoggedRobot {
    public chooser chooser;
    public pathPlannerChooser pathPlanner;
    private Command m_autonomousCommand;
    public SendableChooser<String> autonChooser;
    public static CTREConfigs ctreConfigs;

    public Robot() {
        super(0.02);
    }

    @Override
    public void robotInit() {
        /* Advantage Kit - Start */
        Logger logger = Logger.getInstance();
        setUseTiming(Constants.softwareConstants.getMode() != Constants.softwareConstants.Mode.REPLAY);
        logger.recordMetadata("Robot", Constants.softwareConstants.getRobot().toString());
        logger.recordMetadata("TuningMode", Boolean.toString(Constants.softwareConstants.tuningMode));
        // logger.recordMetadata("RuntimeType", getRuntimeType().toString());
        // logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
        // logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
        // logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
        // logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
        // logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);
        // switch (BuildConstants.DIRTY) {
        //   case 0:
        //     logger.recordMetadata("GitDirty", "All changes committed");
        //     break;
        //   case 1:
        //     logger.recordMetadata("GitDirty", "Uncomitted changes");
        //     break;
        //   default:
        //     logger.recordMetadata("GitDirty", "Unknown");
        //     break;
        // }
    
        // switch (Constants.softwareConstants.getMode()) {
        //   case REAL:
        //     String folder = Constants.softwareConstants.logFolders.get(Constants.softwareConstants.getRobot());
        //     if (folder != null) {
        //       logger.addDataReceiver(new WPILOGWriter(folder));
        //     }
        //     logger.addDataReceiver(new NT4Publisher());
        //     LoggedPowerDistribution.getInstance();
        //     break;
    
        //   case SIM:
        //     logger.addDataReceiver(new NT4Publisher());
        //     break;
    
        //   case REPLAY:
        //     String path = LogFileUtil.findReplayLog();
        //     logger.setReplaySource(new WPILOGReader(path));
        //     logger.addDataReceiver(
        //         new WPILOGWriter(LogFileUtil.addPathSuffix(path, "_sim")));
        //     break;
        // }
        
        logger.start();

        /* Advantage Kit - End */

        PathPlannerServer.startServer(1232);
        
        ctreConfigs = new CTREConfigs();
        new RobotContainer();

        DriverStation.silenceJoystickConnectionWarning(true);

        autonChooser = new SendableChooser<>();
        autonChooser.setDefaultOption("Nothing", "null");
        // autonChooser.addOption("Path 1", "Path1.json");
        // autonChooser.addOption("Coop No Bump", "CoopertitionNoBump.json");
        autonChooser.addOption("Place and Charge Path", "1pieceAndCharge");
        SmartDashboard.putData("Autonomous Command", autonChooser);

        // CommandScheduler.getInstance().onCommandInitialize((Command c) -> {DataLogManager.log("INITIALIZED: " + c.getName());});
        // CommandScheduler.getInstance().onCommandFinish((Command c) -> {DataLogManager.log("FINISHED: " + c.getName());});
        // CommandScheduler.getInstance().onCommandInterrupt((Command c) -> {DataLogManager.log("INTERUPTED: " + c.getName());});
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        // LoggyThingManager.getInstance().periodic();
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

        new Trigger(() -> pathPlanner.autonFinished).onTrue(new balance().repeatedly().until(() -> Math.abs(Swerve.getInstance().getPitchAngle()) <= 0.05));
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

}
