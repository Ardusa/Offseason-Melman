// package frc.robot.Subsystems.Vision;

// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Constants;
// import frc.robot.Subsystems.Drivetrain.Swerve;

// public class goToTag extends CommandBase {

//     private Vision mVision = Vision.getInstance();
//     private Swerve mSwerve = Swerve.getInstance();

//     private double dist, theta;
//     private int tagID;

//     private PIDController pid;

//     public goToTag() {
//         this.addRequirements(mVision, mSwerve);
//         this.setName("Go to tag " + mVision.getAprilTagID());
//     }

//     @Override
//     public void initialize() {
//         dist = SmartDashboard.getNumber("tx", 0);
//         tagID = (int) SmartDashboard.getNumber("tid", 0);
//     }

//     @Override
//     public void execute() {
//         new Dr
//     }
// }
