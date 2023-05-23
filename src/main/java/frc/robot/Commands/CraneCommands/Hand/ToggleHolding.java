package frc.robot.Commands.CraneCommands.Hand;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import frc.robot.Subsystems.CraneAssembly.Hand;

public class ToggleHolding extends InstantCommand{

    private Hand mHand = Hand.getInstance();
    private XboxController xManip = RobotContainer.xManip;

    /**
     * Change the Cone Holding State, does not affect Claw State
     */
    public ToggleHolding() {
        addRequirements(mHand);
    }

    @Override
    public void initialize() {
        mHand.setHolding(!mHand.getHolding());
        
        if(mHand.getHolding()) {
            xManip.setRumble(RumbleType.kLeftRumble, 0.25);
        } else {
            xManip.setRumble(RumbleType.kRightRumble, 0.25);
        }
    }
}
