package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

    private static Drivetrain Instance;

    private final CANSparkMax PL_lm1 = new CANSparkMax(Constants.drivetrainLeftMotorMaster_ID, MotorType.kBrushless);
    private final CANSparkMax PL_lm2 = new CANSparkMax(Constants.drivetrainLeftMotorSlave_ID, MotorType.kBrushless);
    private final CANSparkMax PL_rm1 = new CANSparkMax(Constants.drivetrainRightMotorMaster_ID, MotorType.kBrushless);
    private final CANSparkMax PL_rm2 = new CANSparkMax(Constants.drivetrainRightMotorSlave_ID, MotorType.kBrushless);

    public static Drivetrain getInstance() {
        if (Instance == null) {
            Instance = new Drivetrain();
        }
        return Instance;
    }

    public Drivetrain() {
        PL_lm2.follow(PL_lm1);
        PL_rm2.follow(PL_rm1);

        PL_rm1.setInverted(false);
        // PL_lm2.setInverted(true);
        PL_lm1.setInverted(true);
    }

    public void periodic() {

    }

    public void simulationPeriodic() {

    }

    public void setSpeeds(double lm1, double rm1) {
        PL_lm1.set(lm1);
        SmartDashboard.putNumber("Left Motor Speed", lm1 * -1);
        PL_rm1.set(rm1);
        SmartDashboard.putNumber("Right Motor Speed", rm1 * -1);
    }
}