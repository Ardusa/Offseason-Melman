package frc.robot.Commands.Lights;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.Lights;
import frc.robot.Subsystems.Lighting;

public class LightReqCMD extends InstantCommand {
    private final Lighting mLighting = Lighting.getInstance();
    Timer timer = Lighting.timer;
    boolean cone;

    /**
     * Flip flop between Cone and Cube indicator lights
     */
    public LightReqCMD() {
        this.addRequirements(mLighting);
    }
    
    @Override
    public void initialize() {
        timer.reset();
        timer.start();

    }

    @Override
    public void execute() {
        if (Lighting.isCone) {
            Lighting.PWMVal = Lights.kConeStatic;
            mLighting.setLights(Lights.kConeBlink);
            this.setName("Requesting Cone");         
        } else {
            Lighting.PWMVal = Lights.kCubeStatic;           
            mLighting.setLights(Lights.kCubeBlink);
            this.setName("Requesting Cube");
        }
        Lighting.isCone = !Lighting.isCone;
    }
}