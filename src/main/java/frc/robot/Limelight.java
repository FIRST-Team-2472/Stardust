package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private final NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");


    
    public enum Pipeline {}
    

    public boolean isTargetSpoted() {
        return limelight.getEntry("tv").getDouble(0) == 1.0;
    }
    public double targetXAngleFromCenter() {
        //return limelight.getEntry("tx").getDouble(Double.NaN);
        return limelight.getEntry("ty").getDouble(Double.NaN);

    }
    public double targetYAngleFromCenter() {
        //return limelight.getEntry("tx").getDouble(Double.NaN);
        return limelight.getEntry("tx").getDouble(Double.NaN);
    }
    public double targetArea() {
        return limelight.getEntry("ta").getDouble(Double.NaN);
    }
    public double targetRotation() {
        return limelight.getEntry("ts").getDouble(Double.NaN);
    }
    public double timeSinceLastUpdate() {
        return limelight.getEntry("tl").getDouble(Double.NaN);
    }
    public double targetShortestSideLength() {
        return limelight.getEntry("tshort").getDouble(Double.NaN);
    }
    public double targetLongestSideLength() {
        return limelight.getEntry("tlong").getDouble(Double.NaN);
    }
    public double targetHorizontalSideLength() {
        return limelight.getEntry("thor").getDouble(Double.NaN);
    }
    public double targetVerticalSideLength() {
        return limelight.getEntry("tvert").getDouble(Double.NaN);
    }
    public double limelightPipeline() {
        return limelight.getEntry("getpipe").getDouble(-1);
    }

    public static final int LED_DEFAULT_TO_PIPELINE = 0;
    public static final int LED_FORCE_OFF = 1;
    public static final int LED_FORCE_ON = 3;
    public static final int LED_FORCE_BLINK = 2;

    public void setLedMode(int mode) {
        limelight.getEntry("ledMode").setNumber(mode);
    }
    public void setDriverCamMode(boolean yes) {
        limelight.getEntry("ledMode").setNumber(yes ? 1 : 0 );
    }
    public static final int FIND_TARGET = 0;
    public static final int FIND_BALL = 1;

    public void setPipeLine(int pipelineMode) {
        limelight.getEntry("pipeLine").setNumber(pipelineMode);
    }
    public static final int STANDARD_STREAM = 0;
    public static final int PIP_MAIN_STREAM = 1;
    public static final int PIP_SECONDARY_STREAM = 2;

    public void setStream(int streamMode) {
        limelight.getEntry("stream").setNumber(streamMode);
    }
    public static final int STOP_TAKING_SNAPSHOTS = 0;
    public static final int TAKE_TWO_SNAPSHOTS_A_SECOND = 0;

    public void setSnapshot(int snapshotMode) {
        limelight.getEntry("pipeLine").setNumber(snapshotMode);
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