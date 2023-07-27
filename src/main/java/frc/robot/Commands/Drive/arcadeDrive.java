package frc.robot.Commands.Drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Drivetrain;

public class arcadeDrive extends CommandBase {
    private Drivetrain mDrivetrain;
    private DoubleSupplier leftStick, rightStick;

    public arcadeDrive(DoubleSupplier left, DoubleSupplier right) {
        leftStick = left;
        rightStick = right;

        mDrivetrain = Drivetrain.getInstance();
        addRequirements(mDrivetrain);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double leftAxis = leftStick.getAsDouble();
        double rightAxis = -rightStick.getAsDouble();
        double leftMotor = 0;
        double rightMotor = 0;
        double deadzone = .08;

        if (leftAxis > deadzone || leftAxis < (-deadzone) || rightAxis > deadzone || rightAxis < (-deadzone)) {
            leftMotor = (leftAxis + rightAxis) * Constants.DRIVETRAIN_MOD_LEFT;
            rightMotor = (leftAxis - rightAxis)
                    * (leftAxis > 0 ? Constants.DRIVETRAIN_MOD_RIGHT : Constants.DRIVETRAIN_MOD_RIGHT_B);
        }
        mDrivetrain.setSpeeds(leftMotor, rightMotor);
    }
    /*
     * if (leftAxis >= deadzone || leftAxis <= -deadzone) {
     * // || rightAxis >= deadzone || rightAxis <= -deadzone) {
     * if (rightAxis <= -deadzone) { //Left Turn
     * leftMotor = (leftAxis) - (Constants.DRIVETRAIN_MOD * (rightAxis * -1));
     * // rightMotor = (leftAxis) + (Constants.DRIVETRAIN_MOD * (rightAxis * -1));
     * 
     * rightMotor = leftAxis;
     * } else { //Right Turn
     * leftMotor = leftAxis;
     * 
     * // leftMotor = (leftAxis) - (Constants.DRIVETRAIN_MOD * rightAxis);
     * rightMotor = (leftAxis) - (Constants.DRIVETRAIN_MOD * rightAxis);
     * }
     * }
     */

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}