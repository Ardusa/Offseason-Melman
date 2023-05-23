package frc.robot.Commands.CraneCommands.Hand;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.CraneAssembly.Hand;

public class ToggleGrip extends CommandBase{

    private Hand mHand = Hand.getInstance();
    
    /**
     * Close or Open Claw depending on current state
     */
    public ToggleGrip() {
        addRequirements(mHand);
    }

    @Override
    public void execute() {
        mHand.setGripping(!mHand.getGripping());
    }
}
