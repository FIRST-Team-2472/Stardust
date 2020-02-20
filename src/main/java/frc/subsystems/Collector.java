package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Collector {
    private TalonSRX conveyor;
    private TalonSRX frontWheels;
    
    public Collector (int conveyorID, int frontWheelsID) {
        conveyor = new TalonSRX(conveyorID);
        frontWheels = new TalonSRX(frontWheelsID);

        conveyor.setInverted(true);
    }

    public void runCollector(double speed) {
        runFrontWheels(speed);
        runConveyor(speed);
    }
    public void runFrontWheels(double speed) {
        frontWheels.set(ControlMode.PercentOutput, speed);
    }
    public void runConveyor(double speed) {
        conveyor.set(ControlMode.PercentOutput, speed);
    }
}