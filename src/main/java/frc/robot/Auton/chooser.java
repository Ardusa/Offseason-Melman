package frc.robot.Auton;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
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
    private String selectedAuton;
    private final String baseFolder = "autonPaths/";
    private Path AutonPath;
    private ProfiledPIDController thetaController = 
        new ProfiledPIDController(
        Constants.AutoConstants.kPThetaController, 0, 0,
        Constants.AutoConstants.kThetaControllerConstraints
    );

    private final Swerve mSwerve = Swerve.getInstance();

    /** Add .json to files */
    public chooser(String selected) {
        this.selectedAuton = baseFolder + selected;
        AutonPath = Filesystem.getDeployDirectory().toPath().resolve(selectedAuton);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public Command getTrajectory() {
        try {
            return new SwerveControllerCommand(
                TrajectoryUtil.fromPathweaverJson(AutonPath),
                mSwerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                mSwerve::setModuleStates,
                mSwerve
            );
        } catch (IOException ex) {
            DriverStation.reportError("Auton Path not valid", ex.getStackTrace());
            return Commands.print("Auton Path not valid: " + AutonPath.toString());
        }
    }
}
