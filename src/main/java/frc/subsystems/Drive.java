package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Drive {

    private TalonSRX backLeft;
    private TalonSRX backRight;
    private TalonSRX frontLeft;
    private TalonSRX frontRight;

    public Drive(int backleftID, int backrightID, int frontleftID, int frontrightID) {
        backLeft = new TalonSRX(backleftID);
        backRight = new TalonSRX(backrightID);
        frontLeft = new TalonSRX(frontleftID);
        frontRight = new TalonSRX(frontrightID);

        // Not slaved for testing
        backLeft.follow(frontLeft);
        backLeft.setInverted(InvertType.FollowMaster);
        backRight.follow(frontRight);
        backRight.setInverted(InvertType.FollowMaster);


        frontRight.setInverted(false);
        

        //setupMotionMagic(0, 0, 0, 0, 500, 100);

    }

    public void setupMotionMagic(double f, double p, double i, double d, int velocity, int acceleration) {
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
        frontLeft.configMotionCruiseVelocity(velocity, 30);

        // Motion magic acceleration is 50 counts
        frontLeft.configMotionAcceleration(acceleration, 30);

        // Zero the sensor once on robot boot up
        frontLeft.setSelectedSensorPosition(0, 0, 30);

        frontLeft.config_kP(0, p);
        frontLeft.config_kI(0, i);
        frontLeft.config_kD(0, d);
        frontLeft.config_kF(0, f);

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
        frontRight.configMotionCruiseVelocity(velocity, 30);

        // Motion magic acceleration is 50 counts
        frontRight.configMotionAcceleration(acceleration, 30);

        // Zero the sensor once on robot boot up
        frontRight.setSelectedSensorPosition(0, 0, 30);

        frontRight.config_kP(0, p);
        frontRight.config_kI(0, i);
        frontRight.config_kD(0, d);
        frontRight.config_kF(0, f);

    }

    public static final int COUNTS_PER_FOOT = 5215;

    public void driverFeet(double meters) {
        System.out.println("alskjfawleijafweofijwef");
        frontLeft.set(ControlMode.MotionMagic, meters * COUNTS_PER_FOOT * 99999);
        frontRight.set(ControlMode.MotionMagic, meters * COUNTS_PER_FOOT * 99999);
    }

    public int driveError() {
        return frontLeft.getClosedLoopError();
    }

    /**
     * This will run the left side wheels of the robot and the right side wheels of
     * the robot at the given speeds
     */
    public void tankDrive(double left, double right) {
        runBackLeft(left * -1);
        runFrontLeft(left * -1);
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