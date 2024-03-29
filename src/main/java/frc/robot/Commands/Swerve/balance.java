package frc.robot.Commands.Swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Drivetrain.Swerve;

public class balance extends CommandBase {
    private static final Swerve mSwerve = Swerve.getInstance();
    private static final PIDController balancePID = new PIDController(Constants.Swerve.balancePID.kP, Constants.Swerve.balancePID.kI, Constants.Swerve.balancePID.kD);

    /**
     * Receive input from Gyro and Maintain Robot Pitch at 0
     */
    public balance() {
        addRequirements(mSwerve);
        setName("Balance");
    }

    @Override
    public void execute() {
        mSwerve.drive(new Translation2d(-balancePID.calculate(0, mSwerve.getPitchAngle()) * 4.5, 0), 0, false, true);
    }

    @Override
    public void end(boolean interrupted) {
        mSwerve.drive(new Translation2d(0, 0), 0, false, false);
        balancePID.close();
    }

}
