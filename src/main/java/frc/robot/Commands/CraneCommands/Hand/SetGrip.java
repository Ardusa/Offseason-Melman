package frc.robot.Commands.CraneCommands.Hand;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.CraneAssembly.Hand;

public class SetGrip extends CommandBase{

    private Hand mHand = Hand.getInstance();
    
    /**
     * Will Close the Claw, and then Open the claw in end() function
     */
    public SetGrip() {
        addRequirements(mHand);
    }

    @Override
    public void execute() {
        mHand.setGripping(true);
    }

    @Override
    public void end(boolean interrupted) {
        mHand.setGripping(false);
        super.end(interrupted);
    }
}
