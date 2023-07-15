package frc.robot.Subsystems.Vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight extends SubsystemBase {

    private static LimeLight mInstance;

    public static LimeLight getInstance() {
        if (mInstance == null) {
            mInstance = new LimeLight();
        }
        return mInstance;
    }

    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    private NetworkTableEntry tx = table.getEntry("tx");
    private NetworkTableEntry ty = table.getEntry("tx");
    private NetworkTableEntry ta = table.getEntry("tx");
    private NetworkTableEntry tv = table.getEntry("tv");
    private NetworkTableEntry tid = table.getEntry("tid");

    private static double x;
    private static double y;
    private static double area;
    private static boolean v;
    private static double id;

    private LimeLight() {}

    @Override
    public void periodic() {
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        area = ta.getDouble(0.0);
        v = tv.getBoolean(false);
        id = tid.getDouble(-1);

        // post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area);
        SmartDashboard.putBoolean("LimelightV", v);
        SmartDashboard.putNumber("AprilTag_ID", id);
    }

    /** get tx */
    public static double getX() {
        return x;
    }

    /** get ty */
    public static double getY() {
        return y;
    }

    /** get area */
    public static double getArea() {
        return area;
    }

    public static boolean getObject() {
        return v;
    }

    public static double getID() {
        return id;
    }

}