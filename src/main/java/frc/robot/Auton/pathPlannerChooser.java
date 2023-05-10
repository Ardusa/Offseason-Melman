package frc.robot.Auton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
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
            mSwerve.resetOdometry(pathPlannerTrajectory.getInitialHolonomicPose());
        }
        eventMap1.put("done", new InstantCommand(() -> autonFinished = true));
        
        // eventMap1.put("start", Commands.print("Start"));
        // eventMap1.put("print", Commands.print("print"));
        // eventMap1.put("testmark", Commands.print("testmark"));
        // eventMap1.put("balance", new balance());

        // autonPoses();
    }

    public Command generateTrajectory() {
        if (selectedAuton.equals("null")) {
            return Commands.print("Null Path");
        } else {
            return new FollowPathWithEvents(
                new PPSwerveControllerCommand(
                        pathPlannerTrajectory,
                        mSwerve::getPose,
                        new PIDController(10, 0, 0),
                        new PIDController(8, 0, 0),
                        new PIDController(1, 0, 0),
                        mSwerve::setChassisSpeeds,
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

    public void autonPoses() {
        // String poses = "[";
        List<Double> doublePoses = new ArrayList<>();
        Double[] array = {};
        // for (State state : pathPlannerTrajectory.getStates()) {
        //     poses += (state.poseMeters.getX() + "," + state.poseMeters.getY() + "," + state.poseMeters.getRotation().getDegrees() + ",");
        // }
        // poses += (pathPlannerTrajectory.getEndState().poseMeters.getX() + "," + pathPlannerTrajectory.getEndState().poseMeters.getY() + "," + pathPlannerTrajectory.getEndState().poseMeters.getRotation().getDegrees() + "]");
        
        // SmartDashboard.putString("Autonomous Command/Auton Poses", poses);
        // return poses;

        /////////////////////
        for (State state : pathPlannerTrajectory.getStates()) {
            doublePoses.add(state.poseMeters.getX());
            doublePoses.add(state.poseMeters.getY());
            doublePoses.add(state.poseMeters.getRotation().getDegrees());
            // poses += (state.poseMeters.getX() + "," + state.poseMeters.getY() + "," + state.poseMeters.getRotation().getDegrees() + ",");
        }
        // poses += (pathPlannerTrajectory.getEndState().poseMeters.getX() + "," + pathPlannerTrajectory.getEndState().poseMeters.getY() + "," + pathPlannerTrajectory.getEndState().poseMeters.getRotation().getDegrees() + "]");
        SmartDashboard.putNumberArray("Auton Poses", doublePoses.toArray(array));
        // return doublePoses;
    }
}
