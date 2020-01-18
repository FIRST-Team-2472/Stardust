package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Shooter {
    private TalonSRX leftFlyWheel;
    private TalonSRX rightFlyWheel;

    public Shooter(int leftFlyWheelID, int rightFlyWheelID) {
        leftFlyWheel = new TalonSRX(leftFlyWheelID);
        rightFlyWheel = new TalonSRX(rightFlyWheelID);
    }

    public void runFlyWheel(double speed) {
        runLeftFlyWheel(speed);
        runRightFlyWheel(speed);
    }

    public void runLeftFlyWheel(double speed) {
        leftFlyWheel.set(ControlMode.PercentOutput, speed);
    }

    public void runRightFlyWheel(double speed) {
        rightFlyWheel.set(ControlMode.PercentOutput, speed);
    }
}