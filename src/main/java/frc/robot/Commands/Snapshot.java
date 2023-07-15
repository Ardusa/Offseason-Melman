package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Vision.LimeLight;

public class Snapshot extends CommandBase {

    private double id, tx;

    private LimeLight limelight = LimeLight.getInstance();

    public Snapshot() {
        // this.addRequirements(drivetrain);
    }
}
