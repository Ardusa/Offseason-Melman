package frc.robot.Commands.Teleop;


import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Custom.Utils;
import frc.robot.Subsystems.Drivetrain.Swerve;


public class defaultSwerve extends CommandBase {    
    private Swerve mSwerve = Swerve.getInstance();    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;
    private BooleanSupplier BoB; // Baby on Board

    /**
     * Default Swerve Code
     * @param translationSup (DoubleSupplier) Forward & Backwards
     * @param strafeSup (DoubleSupplier) Left & Right
     * @param rotationSup (DoubleSupplier) Turning angle
     * @param robotCentricSup (BooleanSupplier) Whether or not the robot drives from its own POV
     * @param BoB (BooleanSupplier) Decrease speed for sensitive movement
     */
    public defaultSwerve(DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup, BooleanSupplier BoB) {
        addRequirements(mSwerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        this.BoB = BoB;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband*/
        double translationVal = Utils.customDeadzone(translationSup.getAsDouble());
        double strafeVal = Utils.customDeadzone(strafeSup.getAsDouble());
        double rotationVal = Utils.customDeadzone(rotationSup.getAsDouble());

        if(BoB.getAsBoolean()) {
            translationVal *= 0.2;
            strafeVal *= 0.2;
            rotationVal *= 0.2;
        }

        /* Drive */
        mSwerve.drive(
            new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
            rotationVal * Constants.Swerve.maxAngularVelocity, 
            !robotCentricSup.getAsBoolean(), 
            true
        );
    }
}