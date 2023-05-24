package frc.robot.Commands.Swerve;

import java.util.ArrayList;
import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Drivetrain.Swerve;

public class AutoPilot {
    private Swerve mSwerve = Swerve.getInstance();

    private Field2d mField = mSwerve.getField();

    private PathPlannerTrajectory bumpPath, noBumpPath, chargePadPath;
    private PathPlannerTrajectory closestPath, pathToPath, finalPath;
    private List<PathPlannerTrajectory> paths = new ArrayList<>();
    private List<PathPoint> pathPoints = new ArrayList<>();
    private double distanceFromPath;
    private Pose2d initialPose;

    // private FieldObject2d chargePad = mField.getObject("Charge Pad");
    // private FieldObject2d dividingWall = mField.getObject("Dividing Wall");
    // private FieldObject2d O_Block = mField.getObject("Enemy Community");
    // private FieldObject2d O_BlockSchool = mField.getObject("Enemy Substation");

    public AutoPilot() {
        try {
            noBumpPath = PathPlannerTrajectory.transformTrajectoryForAlliance(
                PathPlanner.loadPath("AutoPilotNoBump",
                PathPlanner.getConstraintsFromPath("AutoPilotNoBump")),
                DriverStation.getAlliance()
            );
            paths.add(noBumpPath);
        } catch (NullPointerException e) {
            System.out.println("No path found for noBump");
        }

        // bumpPath = PathPlannerTrajectory.transformTrajectoryForAlliance(
        //     PathPlanner.loadPath("AutoPilot-noBump",
        //     PathPlanner.getConstraintsFromPath("AutoPilot-bump")),
        //     DriverStation.getAlliance()
        // );
        // paths.add(bumpPath);

        // chargePadPath = PathPlannerTrajectory.transformTrajectoryForAlliance(
        //     PathPlanner.loadPath("AutoPilot-chargePadPath",
        //     PathPlanner.getConstraintsFromPath("AutoPilot-chargePadPath")),
        //     DriverStation.getAlliance()
        // );
        // paths.add(chargePadPath);

        initialPose = mSwerve.getPose();

        for (PathPlannerTrajectory path : paths) {
            if (closestPath == null) {
                closestPath = path;
                distanceFromPath = (path.getInitialHolonomicPose().getTranslation()
                        .getDistance(initialPose.getTranslation()));
            }
            if (path.getInitialHolonomicPose().getTranslation()
                    .getDistance(initialPose.getTranslation()) < distanceFromPath) {
                closestPath = path;
                distanceFromPath = (path.getInitialHolonomicPose().getTranslation()
                        .getDistance(initialPose.getTranslation()));
            }
        }

        pathPoints.add(PathPoint.fromCurrentHolonomicState(initialPose, mSwerve.getFieldVelocity()));
        pathPoints.add(new PathPoint(closestPath.getInitialHolonomicPose().getTranslation(), closestPath.getInitialHolonomicPose().getRotation()));

        pathToPath = PathPlanner.generatePath(new PathConstraints(5, 3), pathPoints);

        finalPath = (PathPlannerTrajectory) pathToPath.concatenate(closestPath);
        mField.getObject("AutoPilot").setTrajectory(finalPath);
    }

    public Command generatePath() {
        return new PPSwerveControllerCommand(
            finalPath,
            mSwerve::getPose,
            new PIDController(10, 0, 0),
            new PIDController(8, 0, 0),
            new PIDController(1, 0, 0),
            mSwerve::setChassisSpeeds,
            mSwerve
        );
    }
}
