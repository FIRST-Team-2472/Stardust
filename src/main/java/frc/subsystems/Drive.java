package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Drive {

    private TalonSRX backLeft;
    private TalonSRX backRight;
    private TalonSRX frontLeft;
    private TalonSRX frontRight;
    public double desiredLeft;
    public double desiredRight;

    public Drive(int backleftID, int backrightID, int frontleftID, int frontrightID) {
        backLeft = new TalonSRX(backleftID);
        backRight = new TalonSRX(backrightID);
        frontLeft = new TalonSRX(frontleftID);
        frontRight = new TalonSRX(frontrightID);

        frontLeft.config_kP(0, 0.005);
        frontLeft.config_kI(0, 0);
        frontLeft.config_kD(0, 0.05);
        frontLeft.config_kF(0, .164);


        backRight.config_kP(0, 0);
        backRight.config_kI(0, 0);
        backRight.config_kD(0, 0);
        backRight.config_kF(0, .164);

        SmartDashboard.putNumber("KP", 0);
        SmartDashboard.putNumber("KI", 0);
        SmartDashboard.putNumber("KD", 0);
        SmartDashboard.putNumber("KF", .164);

        // Not slaved for testing
        backLeft.follow(frontLeft);
        //backLeft.setInverted(InvertType.FollowMaster);
        frontRight.follow(backRight);
        //backRight.setInverted(InvertType.FollowMaster);



        frontRight.setInverted(false);

        frontLeft.setInverted(true);
        backRight.setInverted(false);

        //encoders
        
        backRight.setSensorPhase(true);  // correct encoder to motor direction
     
        // Tell the talon that he has a quad encoder
        backRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
       
        // Set minimum output (closed loop)  to 0 for now
        backRight.configNominalOutputForward(0, 30);
        backRight.configNominalOutputReverse(0, 30);
        
        // Set maximum forward and backward to full speed
        backRight.configPeakOutputForward(1, 30);
        backRight.configPeakOutputReverse(-1, 30);
    
        // Motion magic cruise (max speed) is 100 counts per 100 ms

            backRight.configMotionCruiseVelocity(500, 30);

            backRight.configMotionCruiseVelocity(3000, 30);

            backRight.configMotionCruiseVelocity(3000, 30);

    
        // Motion magic acceleration is 50 counts
            backRight.configMotionAcceleration(100, 30);
    
            // Zero the sensor once on robot boot up 
            backRight.setSelectedSensorPosition(0, 0, 30);

//other side

            frontLeft.setSensorPhase(true);  // correct encoder to motor direction

            // Tell the talon that he has a quad encoder
            frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
           
            // Set minimum output (closed loop)  to 0 for now
            frontLeft.configNominalOutputForward(0, 30);
            frontLeft.configNominalOutputReverse(0, 30);
            
            // Set maximum forward and backward to full speed
            frontLeft.configPeakOutputForward(1, 30);
            frontLeft.configPeakOutputReverse(-1, 30);
        
            // Motion magic cruise (max speed) is 100 counts per 100 ms

                frontLeft.configMotionCruiseVelocity(500, 30);

                frontLeft.configMotionCruiseVelocity(3000, 30);

                frontLeft.configMotionCruiseVelocity(3000, 30);

        
            // Motion magic acceleration is 50 counts
                frontLeft.configMotionAcceleration(100, 30);
        
                // Zero the sensor once on robot boot up 
                frontLeft.setSelectedSensorPosition(0, 0, 30);
        
        
                //setupMotionMagic(0, 0, 0, 0, 500, 100);

    }

    public void setupMotionMagic(double f, double p, double i, double d, int velocity, int acceleration) {


        frontLeft.config_kP(0, p);
        frontLeft.config_kI(0, i);
        frontLeft.config_kD(0, d);
        frontLeft.config_kF(0, f);


        backRight.config_kP(0, p);
        backRight.config_kI(0, i);
        backRight.config_kD(0, d);
        backRight.config_kF(0, f);

    }

    public static final int COUNTS_PER_FOOT = 5215;

    public void driverFeet(double meters) {
        System.out.println("alskjfawleijafweofijwef");
        frontLeft.set(ControlMode.MotionMagic, meters * COUNTS_PER_FOOT * 99999);
        frontRight.set(ControlMode.MotionMagic, meters * COUNTS_PER_FOOT * 99999);
    }

    
    public void runBackRightVelocity(double speed) {
        backRight.set(ControlMode.Velocity, speed * 6250);
        desiredRight = (double)speed * 6250;
    }

    public void runFrontLeftVelocity(double speed) {
        frontLeft.set(ControlMode.Velocity, speed * 6250);
        desiredLeft = (double)speed * 6250;
    }

    public int leftDriveError() {
        return (int)desiredLeft - getLeftSpeed();
    }
    
    public int rightDriveError() {
        return (int)desiredRight - getRightSpeed();
    }

    /**
     * This will run the left side wheels of the robot and the right side wheels of
     * the robot at the given speeds
     * @param left [-1.0, 1.0]
     */

    public void tankDrivePower(double left, double right) {
        //runBackLeft(left * -1);
        runFrontLeftPower(left * -1);
        runBackRightPower(right);
        //runFrontRight(right);
    }

    public void tankDriveVelocity(double left, double right) {
        //runBackLeft(left * -1);
        runFrontLeftVelocity(left * -1);
        runBackRightVelocity(right);
        //runFrontRight(right);
    }
    /** Run the back left moter at the given speed */
    public void runBackLeft(double speed) {
        backLeft.set(ControlMode.Velocity, speed * 300);
    }

//Just for testing.
    public void runFrontRight(double speed) {
        frontRight.set(ControlMode.Velocity, speed * 300);
        
    }

    public void runBackRightPower(double speed) {
        backRight.set(ControlMode.PercentOutput, speed);
    }

    public void runFrontLeftPower(double speed) {
        frontLeft.set(ControlMode.PercentOutput, speed);
    }

    public int getRightSpeed() {
        return backRight.getSelectedSensorVelocity();
    }

    public int getLeftSpeed() {
        return frontLeft.getSelectedSensorVelocity()*-1;
    }

    public int getLeftDistance() {
        return frontLeft.getSelectedSensorPosition();
    }

    public void setLeftDistance(int dis) {
        frontLeft.setSelectedSensorPosition(dis);
    }

    public int getRightDistance(){
        return backRight.getSelectedSensorPosition();
    }


    public void setRightDistance(int dis) {
        backRight.setSelectedSensorPosition(dis);
    }

    public void tankDriveMotionMagic(int targetPosR, int targetPosL){
        backRight.set(ControlMode.MotionMagic, targetPosR);
        frontLeft.set(ControlMode.MotionMagic, targetPosL);
    }


    public void zeroCounters(){
        backRight.setSelectedSensorPosition(0);
        frontLeft.setSelectedSensorPosition(0);
    }
}