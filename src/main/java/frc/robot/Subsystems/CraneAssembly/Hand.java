package frc.robot.Subsystems.CraneAssembly;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hand extends SubsystemBase {

    private static Hand mInstance;
    private DoubleSolenoid mSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Hand.mHandSolenoidFwd,Constants.Hand.mHandSolenoidRev);
    public boolean holdingCone = true;
    public boolean gripping = false;

    Map<String, Object> properties = new HashMap<>();

    public static Hand getInstance() {
        if(mInstance == null) {
            mInstance = new Hand();
        }
        return mInstance;
    }

    public Hand() {
        // startTab();
    }

    private void startTab() {
        properties.put("ColorWhenTrue", "#E9E200");
        properties.put("ColorWhenFalse", "#FE00AA");
        Constants.everythingElseLayout.addBoolean("Cone or Cube", () -> holdingCone).withWidget(BuiltInWidgets.kBooleanBox)
                .withProperties(properties);
    }

    public void periodic() {
        if(gripping) {
            mSolenoid.set(DoubleSolenoid.Value.kForward);
        } else {
            mSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        SmartDashboard.putBoolean("/Hand/Gripping", gripping);
        SmartDashboard.putBoolean("/Hand/HoldingCone", holdingCone);        
    }

    public void setGripping(boolean g) {
        gripping = g;
    }

    public boolean getGripping() {
        return mSolenoid.get() == DoubleSolenoid.Value.kForward;
    }

    public void setHolding(boolean h) {
        holdingCone = h;
    }

    public boolean getHolding() {
        return holdingCone;
    }
}