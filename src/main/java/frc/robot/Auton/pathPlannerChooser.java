package frc.robot.Auton;

import java.util.HashMap;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants;
import frc.robot.Subsystems.Drivetrain.Swerve;

public class pathPlannerChooser {
    private final Swerve mSwerve = Swerve.getInstance();
    public boolean autonFinished;
    private PathPlannerTrajectory pathPlannerTrajectory;
    private String selectedAuton = "";
    private ProfiledPIDController thetaController = 
        new ProfiledPIDController(
        Constants.AutoConstants.kPThetaController, 0, 0,
        Constants.AutoConstants.kThetaControllerConstraints
    );

    HashMap<String, Command> eventMap1 = new HashMap<String, Command>();

    /** Do not include file extensions: .exe, .json, .path */
    public pathPlannerChooser(String path) {

        autonFinished = false;
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
        this.selectedAuton = path;
        if (!selectedAuton.equals("null")) {
            pathPlannerTrajectory = PathPlanner.loadPath(
                path,
                PathPlanner.getConstraintsFromPath(path)
            );
            pathPlannerTrajectory = PathPlannerTrajectory.transformTrajectoryForAlliance(pathPlannerTrajectory, DriverStation.getAlliance());
        }
        eventMap1.put("done", new InstantCommand(() -> autonFinished = true));
        
        // eventMap1.put("start", Commands.print("Start"));
        // eventMap1.put("print", Commands.print("print"));
        // eventMap1.put("testmark", Commands.print("testmark"));
        // eventMap1.put("balance", new balance());

    }

    public Command generateTrajectory() {

        if (selectedAuton.equals("null")) {
            return Commands.print("Null Path");
        } else {
            return new FollowPathWithEvents(
                new SwerveControllerCommand(
                    pathPlannerTrajectory,
                    mSwerve::getPose,
                    Constants.Swerve.swerveKinematics,
                    new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                    new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                    thetaController,
                    mSwerve::setModuleStates,
                    mSwerve
                ),
                pathPlannerTrajectory.getMarkers(),
                eventMap1
            );
        }
    }

    public Trajectory getTrajectory() {
        return pathPlannerTrajectory;
    }
}
