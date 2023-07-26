package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;

public class DriveBase extends SubsystemBase {

    private static DriveBase Instance;

    public static final TalonSRX leftMotor1 = new TalonSRX(OperatorConstants.leftMotor1_ID);
    public static final TalonSRX leftMotor2 = new TalonSRX(OperatorConstants.leftMotor2_ID);
    public static final TalonSRX rightMotor1 = new TalonSRX(OperatorConstants.rightMotor1_ID);
    public static final TalonSRX rightMotor2 = new TalonSRX(OperatorConstants.rightMotor2_ID);

    // private Joystick inputJoystick = new Joystick(0);

    public static DriveBase getInstance() {
        if (Instance == null) {
            Instance = new DriveBase();
        }
        return Instance;
    }

    public DriveBase() {
        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);

        // invert motors here
        leftMotor1.setInverted(true);
        // invert motors here
    }

    @Override
    public void periodic() {
    }

    public void setMotors(double leftDriveSpeed, double rightDriveSpeed) {
        leftMotor1.set(ControlMode.PercentOutput, leftDriveSpeed);
        rightMotor1.set(ControlMode.PercentOutput, rightDriveSpeed);
    }

    public void motorSpin() {
        setMotors(-0.5, 0.5);
    }
}