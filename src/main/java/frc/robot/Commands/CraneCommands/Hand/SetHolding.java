package frc.robot.Commands.CraneCommands.Hand;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import frc.robot.Subsystems.CraneAssembly.Hand;

public class SetHolding extends InstantCommand{
    private Hand mHand = Hand.getInstance();
    private XboxController xManip = RobotContainer.xManip;
    private Boolean holding;
    /**
     * @deprecated
     * Use {@link ToggleHolding} instead
     */
    public SetHolding(Boolean holding) {
        this.holding = holding;
        addRequirements(mHand);
    }

    @Override
    public void initialize() {
        mHand.setHolding(holding);

        if(holding) {
            xManip.setRumble(RumbleType.kLeftRumble, 0.25);
        } else {
            xManip.setRumble(RumbleType.kRightRumble, 0.25);
        }
    }
    
}
