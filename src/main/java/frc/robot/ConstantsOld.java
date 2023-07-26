package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.Custom.lib.util.COTSFalconSwerveConstants;
import frc.robot.Custom.lib.util.SwerveModuleConstants;


//Numbering system for drivetrain: 0 - front left, 1 - front right, 2 - back left, 3 - back right
//0.42545 + 0.254/2

public final class ConstantsOld {
    public static final double stickDeadband = 0.1;

    public static final class Swerve {
        public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

        public static final double kRange = 20;

        public static final COTSFalconSwerveConstants chosenModule =
            COTSFalconSwerveConstants.SDSMK4i(COTSFalconSwerveConstants.driveGearRatios.SDSMK4i_L1);

        /* Drivetrain Constants */
        public static final double trackWidth = 0.501; // 19.72 inches
        public static final double wheelBase = 0.615; // 24.21 Inches
        public static final double wheelCircumference = chosenModule.wheelCircumference;

        /* Swerve Kinematics 
         * No need to ever change this unless you are not doing a traditional rectangular/square 4 module swerve */
         public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0),   // Front Left
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),  // Front Right
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),  // Back Left
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0)  // Back Right
        );

        /* Module Gear Ratios */
        public static final double driveGearRatio = chosenModule.driveGearRatio;
        public static final double angleGearRatio = chosenModule.angleGearRatio;

        /* Motor Inverts */
        public static final boolean angleMotorInvert = chosenModule.angleMotorInvert;
        public static final boolean driveMotorInvert = chosenModule.driveMotorInvert;

        /* Angle Encoder Invert */
        public static final boolean canCoderInvert = chosenModule.canCoderInvert;

        /* Swerve Current Limiting */
        public static final int angleContinuousCurrentLimit = 25;
        public static final int anglePeakCurrentLimit = 40;
        public static final double anglePeakCurrentDuration = 0.1;
        public static final boolean angleEnableCurrentLimit = true;

        public static final int driveContinuousCurrentLimit = 35;
        public static final int drivePeakCurrentLimit = 60;
        public static final double drivePeakCurrentDuration = 0.1;
        public static final boolean driveEnableCurrentLimit = true;

        /* These values are used by the drive falcon to ramp in open loop and closed loop driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc */
        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;

        /* Angle Motor PID Values */
        public static final double angleKP = chosenModule.angleKP;
        public static final double angleKI = chosenModule.angleKI;
        public static final double angleKD = chosenModule.angleKD;
        public static final double angleKF = chosenModule.angleKF;

        /* Drive Motor PID Values */
        public static final double driveKP = 0.05;
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.0;
        public static final double driveKF = 0.0;

        /* Drive Motor Characterization Values 
         * Divide SYSID values by 12 to convert from volts to percent output for CTRE */
        public static final double driveKS = (0.32 / 12);
        public static final double driveKV = (1.51 / 12);
        public static final double driveKA = (0.27 / 12);

        /* Swerve Profiling Values */

        /** Meters per Second */
        public static final double maxSpeed = 4.5;
        public static final double BoBmultiplier = 0.3;
        /** Radians per Second */
        public static final double maxAngularVelocity = 10.0;

        /* Neutral Modes */
        public static final NeutralMode angleNeutralMode = NeutralMode.Coast;
        public static final NeutralMode driveNeutralMode = NeutralMode.Brake;

        public static final double targetOffset = 0;

        public static final int Pigeon2_ID = 3;

        /* Module Specific Constants */


        // Numbering system for drivetrain: 0 - front left, 1 - front right, 2 - back left, 3 - back right

        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int driveMotorID = 20;
            public static final int angleMotorID = 21;
            public static final int canCoderID = 22;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(249.873-90);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 {   
            public static final int driveMotorID = 11;
            public static final int angleMotorID = 10;
            public static final int canCoderID = 12;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(9.932-90);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }
        
        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final int driveMotorID = 30;
            public static final int angleMotorID = 31;
            public static final int canCoderID = 32;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(249.609+ 30.586+90);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final int driveMotorID = 40;
            public static final int angleMotorID = 41;
            public static final int canCoderID = 42;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(274.658+90);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        public static final class balancePID {
            public static final double kP = 0.012;
            public static final double kI = 0.00002;
            public static final double kD = 0.002;
        }

        public static final class Odometry {
          // TODO: ADJUST THESE STANDARD DEVIATIONS
          public static final Matrix<N3, N1> STATE_STANDARD_DEVS = new Matrix<>(Nat.N3(), Nat.N1());
          public static final Matrix<N3, N1> VISION_STANDARD_DEVS = new Matrix<>(Nat.N3(), Nat.N1());
          public static final double FaithInTheState = 0.2;
          public static final double PrecisionOfMyVision = 30;
          static {
            STATE_STANDARD_DEVS.set(0, 0, FaithInTheState); // State x position
            STATE_STANDARD_DEVS.set(1, 0, FaithInTheState); // State y position
            STATE_STANDARD_DEVS.set(2, 0, FaithInTheState); // State rotation

            VISION_STANDARD_DEVS.set(0, 0, PrecisionOfMyVision); // Vision x position
            VISION_STANDARD_DEVS.set(1, 0, PrecisionOfMyVision); // Vision y position
            VISION_STANDARD_DEVS.set(2, 0, PrecisionOfMyVision); // Vision rotation
          }

          public static final double MIN_TIME_BETWEEN_LL_UPDATES_MS = 20e-3;
        }

        public static final class homePoints {
          public static final Pose2d kBlueLeft = new Pose2d(2.2, 4.65, new Rotation2d(180));
        }
    }

    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 5;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = 2 * Math.PI;
    
        public static final double kPXController = 10;
        public static final double kPYController = 10;
        public static final double kPThetaController = 1;
    
        /* Constraint for the motion profilied robot angle controller */
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
        new TrapezoidProfile.Constraints(
          kMaxAngularSpeedRadiansPerSecond,
          kMaxAngularSpeedRadiansPerSecondSquared
        );
    }

  public static class motorConstants {
    public static final double falconFreeSpeedRPM = 6380.0;
  }

  public static class OperatorConstants {
    public static class Debounce {
        public static final double kDPad = 0.2;
        public static final double kBumper = 0.1;
        public static final double kButton = 0.1;
        public static final double kStickPress = 0.1;
    }

    public static class CustomDeadzone {

        public static final double kLowerLimitExpFunc = 0.1;
        public static final double kUpperLimitExpFunc = 0.5;
        public static final double kUpperLimitLinFunc = 1;
  
        public static final double kExpFuncConstant = 0.3218;
        public static final double kExpFuncBase = 12.5;
        public static final double kExpFuncMult = 0.25;
  
        public static final double kLinFuncMult = 0.876;
        public static final double kLinFuncOffset = 0.5;
        public static final double kLinFuncConstant = 0.562;
  
        public static final double kNoSpeed = 0;

        public static final double kJoyStickDeadZone = 0.05;
      }
  }

  public static class softwareConstants {
    public static final int PathPlannerLibPort = 5811;
    public static final int AdvantageScopePort = 5900;

    public static final int RightLimeLightPort = 5800;
    public static final int LeftLimeLightPort = 5600;
    public static final int CenterLimeLightPort = 5000;

    
  }

  public static class Lights {
    public static final int blinkinPWM_ID = 0;
    public static final double blinkTime = 7.5;  

    public static final double kConeStatic = 0.11;
    public static final double kConeBlink = 0.15;
    public static final double kCubeStatic = 0.31;
    public static final double kCubeBlink = 0.35;
    public static final double kFireTwinkle = -0.49;
    public static final double kRobostangs = 0.63;
    public static final double kKillLights = 0.99;
  }
}
