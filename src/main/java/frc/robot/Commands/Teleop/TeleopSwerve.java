package frc.robot.Commands.Teleop;


import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Custom.Utils;
import frc.robot.Subsystems.Drivetrain.Swerve;


public class TeleopSwerve extends CommandBase {    
    private Swerve mSwerve = Swerve.getInstance();    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier BoB; // Baby on Board

    /**
     * Default Swerve Code, will also set gyro to 0 relative to field
     * @param translationSup (DoubleSupplier) Forward & Backwards
     * @param strafeSup (DoubleSupplier) Left & Right
     * @param rotationSup (DoubleSupplier) Turning angle
     * @param BoB (BooleanSupplier) Decrease speed for sensitive movement
     */
    public TeleopSwerve(DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier BoB) {
        addRequirements(mSwerve);

        /* Set the param negative could be solution */
        // mSwerve.setGyro(mSwerve.getPose().getRotation().getDegrees());

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.BoB = BoB;
    }

    @Override
    public void initialize() {
        mSwerve.robotCentricSup = false;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband*/
        double translationVal = Utils.customDeadzone(-translationSup.getAsDouble());
        double strafeVal = Utils.customDeadzone(-strafeSup.getAsDouble());
        double rotationVal = Utils.customDeadzone(-rotationSup.getAsDouble());

        if(BoB.getAsBoolean()) {
            translationVal *= Constants.Swerve.BoBmultiplier;
            strafeVal *= Constants.Swerve.BoBmultiplier;
            rotationVal *= Constants.Swerve.BoBmultiplier;
        }

        /* Drive */
        mSwerve.drive(
            new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
            rotationVal * Constants.Swerve.maxAngularVelocity, 
            !mSwerve.robotCentricSup, 
            true
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}