package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Shooter {
    private TalonSRX FlyWheel;

    public Shooter(int FlyWheelID) {
        FlyWheel = new TalonSRX(FlyWheelID);
    }

    public void runFlyWheel(double speed) {
      FlyWheel.set(ControlMode.PercentOutput, speed);    
    }

    // TODO Write a set RPM method
}