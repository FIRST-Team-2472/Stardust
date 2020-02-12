package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Robot;

public class Shooter {
    private TalonSRX FlyWheel;

    public Shooter(int FlyWheelID) {
        FlyWheel = new TalonSRX(FlyWheelID);
    }

    public void runFlyWheel(double speed) {
      FlyWheel.set(ControlMode.PercentOutput, speed);    
    }

    public void fireFlyWheel() {
      FlyWheel.set(ControlMode.PercentOutput, Robot.limelight.distanceCM() /* TODO make speed of flywheel effected by distance from target*/);
    }

    // TODO Write a set RPM method
}