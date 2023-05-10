package frc.robot.Auton;

import java.io.IOException;
import java.nio.file.Path;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants;
import frc.robot.Subsystems.Drivetrain.Swerve;

public class chooser extends CommandBase {
    private String selectedAuton = "";
    private final String baseFolder = "autonPaths/";
    private Path AutonPath;
    public Trajectory trajectory;
    private String nullPath = "autonPaths/null";
    private ProfiledPIDController thetaController = 
        new ProfiledPIDController(
        Constants.AutoConstants.kPThetaController, 0, 0,
        Constants.AutoConstants.kThetaControllerConstraints
    );

    private final Swerve mSwerve = Swerve.getInstance();

    /** Add .json to files */
    public chooser(String selected) {
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
        this.selectedAuton = baseFolder + selected;
        if (!selected.equals("null")) {
            AutonPath = Filesystem.getDeployDirectory().toPath().resolve(selectedAuton);
            try {
                trajectory = TrajectoryUtil.fromPathweaverJson(AutonPath);
            } catch (IOException ex) {
                DriverStation.reportError("Auton Path not valid", ex.getStackTrace());
            }
        }
    }

    public Command generateTrajectory() {
        // return new defaultSwerve(() -> 1, () -> 1, () -> 0, () -> false, () -> false).repeatedly().withTimeout(5);

        if (selectedAuton.equals(nullPath)) {
            return Commands.print("Null Path");
        } else {
            return new PPSwerveControllerCommand(
                PathPlanner.loadPath(selectedAuton, PathPlanner.getConstraintsFromPath(selectedAuton)),
                mSwerve::getPose,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                new PIDController(Constants.AutoConstants.kPThetaController, 0, 0),
                mSwerve::setChassisSpeeds,
                mSwerve
            );
            // return new SwerveControllerCommand(
            //     // TrajectoryUtil.fromPathweaverJson(AutonPath),
            //     trajectory,
            //     mSwerve::getPose,
            //     Constants.Swerve.swerveKinematics,
            //     new PIDController(Constants.AutoConstants.kPXController, 0, 0),
            //     new PIDController(Constants.AutoConstants.kPYController, 0, 0),
            //     thetaController,
            //     mSwerve::setModuleStates,
            //     mSwerve
            // );
        }
    }

    public Trajectory getTrajectory() {
        return trajectory;
    }
}