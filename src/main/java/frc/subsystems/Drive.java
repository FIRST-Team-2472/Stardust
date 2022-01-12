package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

    private TalonSRX leftSlave;
    private TalonSRX rightMaster;
    private TalonSRX leftMaster;
    private TalonSRX rightSlave;
    public double desiredLeft;
    public double desiredRight;
    public PigeonIMU pigeon = new PigeonIMU(2);

    public Drive(int backleftID, int backrightID, int frontleftID, int frontrightID) {
        rightSlave = new TalonSRX(frontrightID);
        rightMaster = new TalonSRX(backrightID);

        leftMaster = new TalonSRX(frontleftID);
        leftSlave = new TalonSRX(backleftID);

        leftMaster.config_kP(0, 0.05);
        leftMaster.config_kI(0, 0);
        leftMaster.config_kD(0, 0.05);
        leftMaster.config_kF(0, -.19);


        rightMaster.config_kP(0, 0.05);
        rightMaster.config_kI(0, 0);
        rightMaster.config_kD(0, 0.05);
        rightMaster.config_kF(0, -.19);

        leftMaster.setInverted(false);
        rightMaster.setInverted(true);

        leftSlave.follow(leftMaster);
        leftSlave.setInverted(InvertType.FollowMaster);
        rightSlave.follow(rightMaster);
        rightSlave.setInverted(InvertType.FollowMaster);

        

        //encoders
        
        rightMaster.setSensorPhase(true);  // correct encoder to motor direction
     
        // Tell the talon that he has a quad encoder
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
       
        // Set minimum output (closed loop)  to 0 for now
        rightMaster.configNominalOutputForward(0, 30);
        rightMaster.configNominalOutputReverse(0, 30);
        
        // Set maximum forward and backward to full speed
        rightMaster.configPeakOutputForward(1, 30);
        rightMaster.configPeakOutputReverse(-1, 30);
    
        // Motion magic cruise (max speed) is 100 counts per 100 ms
            rightMaster.configMotionCruiseVelocity(500, 30);
            rightMaster.configMotionCruiseVelocity(3000, 30);
            rightMaster.configMotionCruiseVelocity(3000, 30);
    
        // Motion magic acceleration is 50 counts
            rightMaster.configMotionAcceleration(100, 30);
    
            // Zero the sensor once on robot boot up 
            rightMaster.setSelectedSensorPosition(0, 0, 30);

//other side

            leftMaster.setSensorPhase(true);  // correct encoder to motor direction

            // Tell the talon that he has a quad encoder
            leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
           
            // Set minimum output (closed loop)  to 0 for now
            leftMaster.configNominalOutputForward(0, 30);
            leftMaster.configNominalOutputReverse(0, 30);
            
            // Set maximum forward and backward to full speed
            leftMaster.configPeakOutputForward(1, 30);
            leftMaster.configPeakOutputReverse(-1, 30);
        
            // Motion magic cruise (max speed) is 100 counts per 100 ms

                leftMaster.configMotionCruiseVelocity(500, 30);

                leftMaster.configMotionCruiseVelocity(3000, 30);

                leftMaster.configMotionCruiseVelocity(3000, 30);
        
            // Motion magic acceleration is 50 counts
                leftMaster.configMotionAcceleration(100, 30);
        
                // Zero the sensor once on robot boot up 
                leftMaster.setSelectedSensorPosition(0, 0, 30);
        
        
                //setupMotionMagic(0, 0, 0, 0, 500, 100);
    }

    public static final int COUNTS_PER_FOOT = 5215;

    public void driverFeet(double meters) {
        leftMaster.set(ControlMode.MotionMagic, meters * COUNTS_PER_FOOT * 99999);
        rightSlave.set(ControlMode.MotionMagic, meters * COUNTS_PER_FOOT * 99999);
    }

    
    public void runBackRightVelocity(double speed) {
        rightMaster.set(ControlMode.Velocity, speed * 6250);
        desiredRight = (double)speed * 6250;
    }

    public void runFrontLeftVelocity(double speed) {
        leftMaster.set(ControlMode.Velocity, speed * 6250);
        desiredLeft = (double)speed * 6250;
    }
//this code is trash btw honestly kys-issaC 
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
        runFrontLeftPower(left);
        runBackRightPower(right);
    }

    public void tankDriveVelocity(double left, double right) {
        runFrontLeftVelocity(left);
        runBackRightVelocity(right);
    }

    public void arcadeDriveVelocity(double y, double x) {
        // y is Joystick Y axis
        // x is Joystick X axis

        if (Math.abs(x) + Math.abs(y) < .5) {
            tankDriveVelocity(y - x, y + x);
        } else {
            // limits the motors from ever going over 75% speed
            double betterX = (x/(Math.abs(x)+Math.abs(y)))*.75;
            double betterY = (y/(Math.abs(x)+Math.abs(y)))*.75;
            tankDriveVelocity(betterY - betterX, betterY + betterX);
        }
    }

    /** Run the back left moter at the given speed */
    //Just for testing
    public void runBackLeft(double speed) {
        leftSlave.set(ControlMode.Velocity, speed * 300);
    }

    //Just for testing.
    public void runFrontRight(double speed) {
        rightSlave.set(ControlMode.Velocity, speed * 300);
    }

    //just for testing
    public void runBackLeftPower(double speed) {
        leftSlave.set(ControlMode.PercentOutput, speed);
    }

    //Just for testing.
    public void runFrontRightPower(double speed) {
        rightSlave.set(ControlMode.PercentOutput, speed);
    }

    public void runBackRightPower(double speed) {
        rightMaster.set(ControlMode.PercentOutput, speed);
    }

    public void runFrontLeftPower(double speed) {
        leftMaster.set(ControlMode.PercentOutput, speed);
    }

    public int getRightSpeed() {
        return (int)rightMaster.getSelectedSensorVelocity();
    }

    public int getLeftSpeed() {
        return (int)leftMaster.getSelectedSensorVelocity();
    }

    public double getLeftDistance() {
        return leftMaster.getSelectedSensorPosition();
    }

    public void setLeftDistance(int dis) {
        leftMaster.setSelectedSensorPosition(dis);
    }

    public double getRightDistance(){
        return rightMaster.getSelectedSensorPosition();
    }

    public void setRightDistance(int dis) {
        rightMaster.setSelectedSensorPosition(dis);
    }

    public void setDistance(int lDis, int rDis) {
        leftMaster.setSelectedSensorPosition(lDis);
        rightMaster.setSelectedSensorPosition(rDis);
    }


    public void tankDriveMotionMagic(int targetPosR, int targetPosL){
        rightMaster.set(ControlMode.MotionMagic, targetPosR);
        leftMaster.set(ControlMode.MotionMagic, targetPosL);
    }

    public void zeroCounters(){
        rightMaster.setSelectedSensorPosition(0);
        leftMaster.setSelectedSensorPosition(0);
        
        
    }

    public void zeroIMU(){
        pigeon.setFusedHeading(0.0, 30);
    }

    public void DoPigeon(){
        PigeonIMU.GeneralStatus genStatus = new PigeonIMU.GeneralStatus();
    
        PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();

        double [] xyz_dps = new double [3];

        /* grab some input data from Pigeon and gamepad*/

        pigeon.getGeneralStatus(genStatus);

        pigeon.getRawGyro(xyz_dps);

        pigeon.getFusedHeading(fusionStatus);

        double currentAngle = fusionStatus.heading;

        int yaw = ((int)currentAngle % 360);

        boolean angleIsGood = (pigeon.getState() == PigeonIMU.PigeonState.Ready) ? true : false;

        double currentAngularRate = xyz_dps[2];
    
        SmartDashboard.putBoolean("Pigeon Is Working?", angleIsGood);
        SmartDashboard.putNumber("Angle", yaw);
        SmartDashboard.putNumber("Rate", currentAngularRate);

    }

    public int getCurrentAngle() {
        PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
        pigeon.getFusedHeading(fusionStatus);
        double currentAngle = fusionStatus.heading;
        return (int)currentAngle;
    }
}