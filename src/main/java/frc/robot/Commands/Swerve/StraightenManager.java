package frc.robot.Commands.Swerve;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Custom.LoggyThings.LoggyPrintCommand;
import frc.robot.Subsystems.Drivetrain.Swerve;
import frc.robot.Subsystems.Vision.Vision;


public class StraightenManager extends SequentialCommandGroup {
    Swerve mDrivetrain = Swerve.getInstance();
    Vision mVision = Vision.getInstance();

    /**
     * Tell drivetrain to straighten against wall
     * @param moveSpeed (double) the speed of drivetrain during transition
     * @param angleSpeed (double)the rotation amount
     * @param isCone (boolean) is there a cone loaded
     */
    public StraightenManager(double moveSpeed, double angleSpeed, boolean isCone) {
        addRequirements(mDrivetrain);
        setName("Straighten Against Wall");

        addCommands(
            new Flatten(angleSpeed),
            new GetToPosition()
        );
    }

    /**
     * Tell drivetrain to straighten against wall
     * @param isCone (boolean) is there a cone loaded
     */
    public StraightenManager(boolean isCone) {
        addRequirements(mDrivetrain);
        setName("Straighten Against Wall");

        addCommands(
            // new Flatten(0),
            new LoggyPrintCommand("starting straighten"),
            new GetToPosition() // 0.2, isCone
        );
    }


}
