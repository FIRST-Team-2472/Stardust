package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Collector {
    private TalonSRX Conveyor;
    
    public Collector (int ConveyorID) {
        Conveyor = new TalonSRX(ConveyorID);
    }
    
    public void runConveyor(double speed) {
        Conveyor.set(ControlMode.PercentOutput, speed);
    }
}