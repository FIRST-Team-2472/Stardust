package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class Collector {
    private final TalonSRX conveyor;
    private final TalonSRX frontWheels;
    private final DoubleSolenoid frontWheelPush;
    
    public Collector (int conveyorID, int frontWheelsID, int frontWheelForwardID, int frontWheelBackID) {
        conveyor = new TalonSRX(conveyorID);
        frontWheels = new TalonSRX(frontWheelsID);
        frontWheelPush = new DoubleSolenoid(16, frontWheelForwardID, frontWheelBackID);
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

    public void pushoutfrontwheel() {
        frontWheelPush.set(Value.kForward);
    }
    public void pushofffrontwheel() {
        frontWheelPush.set(Value.kOff);
    }
    public void pushinfrontwheel() {
        frontWheelPush.set(Value.kReverse);
    }
}