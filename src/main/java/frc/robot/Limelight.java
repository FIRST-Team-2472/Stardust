package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    public Limelight() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = table.getEntry("tx");
        NetworkTableEntry ty = table.getEntry("ty");
        NetworkTableEntry ta = table.getEntry("ta");
        table.getEntry("ledMode").setNumber(0);
        table.getEntry("camMode").setNumber(0);
        table.getEntry("pipeline").setNumber(0);

    }

    

    public boolean isTargetSpoted() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1.0;
    }
    public double targetXAngleFromCenter() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(-999);
    }
    public double distanceCM() {
        double targetAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double cameraHeight = 1.0668;
        double targetHeight = 2.54635;
        double cameraAngle = 25;
        double d = (targetHeight-cameraHeight) / Math.tan(cameraAngle+targetAngle);
        return d;
    }
}