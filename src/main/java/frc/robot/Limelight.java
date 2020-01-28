package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    

    public boolean isTargetSpoted() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1.0;
    }
    public double distanceAngle() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
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