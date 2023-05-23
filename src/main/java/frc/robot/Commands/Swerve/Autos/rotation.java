package frc.robot.Commands.Swerve.Autos;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Drivetrain.Swerve;
import frc.robot.Subsystems.Vision.Vision;

public class rotation extends CommandBase {
    Swerve mSwerve;
    Vision mVision = Vision.getInstance();
    boolean isReversed = false;
    double offset = 0;
    PIDController pid = new PIDController(0.3, 0,0 );

    double startAngle = 0;
    double endAngle = 0;

    public rotation(double rotation) {
        offset = rotation;
        mSwerve = Swerve.getInstance();
        addRequirements(Swerve.getInstance());
    }

    @Override
    public void initialize() {
        double gyroAngle = mSwerve.getYaw().getDegrees();
        endAngle = gyroAngle + offset;
    }

    @Override
    public void execute() {
        double gyroAngle = mSwerve.getYaw().getDegrees();
        startAngle = gyroAngle;
        // if (isReversed) {
        //     mSwerve.drive(new Translation2d(0, 0), (pid.calculate((360 + startAngle), endAngle + 360)), false, true);
        // } else {
        
        mSwerve.drive(new Translation2d(0, 0), (pid.calculate(startAngle, endAngle)), false, false);

        System.out.println(endAngle - startAngle);
        // }
    }

    @Override
    public boolean isFinished() {
        System.out.println(startAngle + "," + endAngle + "," + mSwerve.getYaw().getDegrees());
        if (startAngle-endAngle <= 1) {
            return true;
        }
        return false;
    }
}
