// package frc.robot.Commands;

// import java.util.Collection;

// import com.ctre.phoenix.motorcontrol.can.TalonFX;
// import com.ctre.phoenix.music.Orchestra;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Subsystems.Drivetrain.Swerve;

// public class Music extends CommandBase {
//     private final Swerve mSwerve = Swerve.getInstance();
//     private Orchestra mOrchestra;
//     private Collection<TalonFX> talons;

//     public Music() {
//         addRequirements(mSwerve); // Add all motor inclding subsystems
//         talons.addAll(mSwerve.mSwerveMods[0].getTalons());
//         talons.addAll(mSwerve.mSwerveMods[1].getTalons());
//         talons.addAll(mSwerve.mSwerveMods[2].getTalons());
//         talons.addAll(mSwerve.mSwerveMods[3].getTalons());
//         mOrchestra = new Orchestra(talons);
//     }
// }
