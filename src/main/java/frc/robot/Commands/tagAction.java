package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class tagAction extends CommandBase {
    
    private double idNum, tx, distance;

    public tagAction(double idNum, double tx, double dist) {
        this.idNum = idNum;
        this.tx = tx;
        distance = dist;
    }
}
