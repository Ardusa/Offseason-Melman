package frc.robot.Custom;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;

import frc.robot.ConstantsOld;

public final class CTREConfigs {
    public TalonFXConfiguration swerveAngleFXConfig;
    public TalonFXConfiguration swerveDriveFXConfig;
    public CANCoderConfiguration swerveCanCoderConfig;

    public CTREConfigs(){
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANCoderConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
            ConstantsOld.Swerve.angleEnableCurrentLimit, 
            ConstantsOld.Swerve.angleContinuousCurrentLimit, 
            ConstantsOld.Swerve.anglePeakCurrentLimit, 
            ConstantsOld.Swerve.anglePeakCurrentDuration);

        swerveAngleFXConfig.slot0.kP = ConstantsOld.Swerve.angleKP;
        swerveAngleFXConfig.slot0.kI = ConstantsOld.Swerve.angleKI;
        swerveAngleFXConfig.slot0.kD = ConstantsOld.Swerve.angleKD;
        swerveAngleFXConfig.slot0.kF = ConstantsOld.Swerve.angleKF;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;

        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            ConstantsOld.Swerve.driveEnableCurrentLimit, 
            ConstantsOld.Swerve.driveContinuousCurrentLimit, 
            ConstantsOld.Swerve.drivePeakCurrentLimit, 
            ConstantsOld.Swerve.drivePeakCurrentDuration);

        swerveDriveFXConfig.slot0.kP = ConstantsOld.Swerve.driveKP;
        swerveDriveFXConfig.slot0.kI = ConstantsOld.Swerve.driveKI;
        swerveDriveFXConfig.slot0.kD = ConstantsOld.Swerve.driveKD;
        swerveDriveFXConfig.slot0.kF = ConstantsOld.Swerve.driveKF;        
        swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        swerveDriveFXConfig.openloopRamp = ConstantsOld.Swerve.openLoopRamp;
        swerveDriveFXConfig.closedloopRamp = ConstantsOld.Swerve.closedLoopRamp;
        
        /* Swerve CANCoder Configuration */
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = ConstantsOld.Swerve.canCoderInvert;
        swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
    }
}