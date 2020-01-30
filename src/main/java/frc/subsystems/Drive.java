package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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

        //frontRight.setInverted(true);
        //backRight.setInverted(true);

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