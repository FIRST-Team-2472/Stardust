package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Drive {

    private VictorSPX backLeft;
    private VictorSPX backRight;
    private TalonSRX frontLeft;
    private TalonSRX frontRight;

    public Drive(int backleftID, int backrightID, int frontleftID, int frontrightID) {
        backLeft = new VictorSPX(backleftID);
        backRight = new VictorSPX(backrightID);
        frontLeft = new TalonSRX(frontleftID);
        frontRight = new TalonSRX(frontrightID);

        backLeft.follow(frontLeft);
        backRight.follow(frontRight);

        // frontRight.setInverted(true);
        // backRight.setInverted(true);

        frontLeft.configFactoryDefault();

        // Tell the talon that he has a quad encoder
        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);

        // Set minimum output (closed loop) to 0 for now
        frontLeft.configNominalOutputForward(0, 30);
        frontLeft.configNominalOutputReverse(0, 30);

        // Set maximum forward and backward to full speed
        frontLeft.configPeakOutputForward(1, 30);
        frontLeft.configPeakOutputReverse(-1, 30);

        // Motion magic cruise (max speed) is 100 counts per 100 ms
        frontLeft.configMotionCruiseVelocity(500, 30);

        // Motion magic acceleration is 50 counts
        frontLeft.configMotionAcceleration(100, 30);

        // Zero the sensor once on robot boot up
        frontLeft.setSelectedSensorPosition(0, 0, 30);

        frontLeft.config_kP(0, 0);
        frontLeft.config_kI(0, 0);
        frontLeft.config_kD(0, 0);
        frontLeft.config_kF(0, 0);

        





        frontRight.configFactoryDefault();
        // Tell the talon that he has a quad encoder
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);

        // Set minimum output (closed loop) to 0 for now
        frontRight.configNominalOutputForward(0, 30);
        frontRight.configNominalOutputReverse(0, 30);

        // Set maximum forward and backward to full speed
        frontRight.configPeakOutputForward(1, 30);
        frontRight.configPeakOutputReverse(-1, 30);

        // Motion magic cruise (max speed) is 100 counts per 100 ms
        frontRight.configMotionCruiseVelocity(500, 30);

        // Motion magic acceleration is 50 counts
        frontRight.configMotionAcceleration(100, 30);

        // Zero the sensor once on robot boot up
        frontRight.setSelectedSensorPosition(0, 0, 30);

        frontRight.config_kP(0, 0);
        frontRight.config_kI(0, 0);
        frontRight.config_kD(0, 0);
        frontRight.config_kF(0, 0);


    }

    public static final int COUNT_PER_METER = 2;

    public void driverMeters(double meters) {
        frontLeft.set(ControlMode.MotionMagic, meters * COUNT_PER_METER);
        frontRight.set(ControlMode.MotionMagic, meters * COUNT_PER_METER);
    }
    
    /**
     * This will run the left side wheels of the robot and the right side wheels of
     * the robot at the given speeds
     */
    public void tankDrive(double left, double right) {
        runBackLeft(left);
        runFrontLeft(left);
        runBackRight(right);
        runFrontRight(right);
    }

    /** Run the back left moter at the given speed */
    public void runBackLeft(double speed) {
        backLeft.set(ControlMode.PercentOutput, speed);
    }

    public void runBackRight(double speed) {
        backRight.set(ControlMode.PercentOutput, speed);
    }

    public void runFrontLeft(double speed) {
        frontLeft.set(ControlMode.PercentOutput, speed);
    }

    public void runFrontRight(double speed) {
        frontRight.set(ControlMode.PercentOutput, speed);
    }

}