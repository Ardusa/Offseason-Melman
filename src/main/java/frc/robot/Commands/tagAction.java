package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Commands.Drive.arcadeDrive;

public class tagAction extends CommandBase {
    
    private double idNum, tx, distance, output;

    public tagAction(double idNum, double tx) {
        this.idNum = idNum;
        this.tx = tx;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (tx < -0.15) {
            output = -0.2;
        } else if (tx > 0.15) {
            output = 0.2;
        } else {
            output = 0;
        }
        new arcadeDrive(() -> 0, () -> output).schedule();
    }

    // Problem Currently: Once the trigger button (b) is clicked, I am no longer able to tele-op control the robot. And the robot just stops moving
}
