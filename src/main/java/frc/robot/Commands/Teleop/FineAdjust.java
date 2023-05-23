package frc.robot.Commands.Teleop;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.CraneAssembly.Arm;

public class FineAdjust extends CommandBase {
    DoubleSupplier shoulder, elbow;
    private Arm mArm = Arm.getInstance();

    /**
     * Manually adjust the {@link Arm}
     * @param shoulder DoubleSupplier for shoulder movement
     * @param elbow DoubleSupplier for elbow movement
     */
    public FineAdjust(DoubleSupplier shoulder, DoubleSupplier elbow) {
        addRequirements(mArm);
        setName("FineAdjust");
        this.shoulder = shoulder;
        this.elbow = elbow;
    }

    // @Override
    // public void initialize() {
    //     // System.out.println("Shoulder: " + shoulder);
    //     // System.out.println("Elbow: " + elbow);
    // }

    @Override
    public void execute() {
        // System.out.println("Shoulder: " + shoulder.getAsDouble());
        mArm.setPower(-shoulder.getAsDouble(), elbow.getAsDouble());
    }
}
