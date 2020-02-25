package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class Collector {
    private final TalonSRX conveyor;
    private final TalonSRX frontWheels;
    private final DoubleSolenoid frontwheelspush;
    
    public Collector (int conveyorID, int frontWheelsID, int frontwheelsforward, int frontwheelsback) {
        conveyor = new TalonSRX(conveyorID);
        frontWheels = new TalonSRX(frontWheelsID);
        frontwheelspush = new DoubleSolenoid(frontwheelsforward, frontwheelsback);
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
    public void pushoutfrontwheels() {
        frontwheelspush.set(Value.kForward);
    }
    public void frontwheelspushoff() {
        frontwheelspush.set(Value.kOff);
    }
    public void frontwheelspushin() {
        frontwheelspush.set(Value.kReverse);
    }
}