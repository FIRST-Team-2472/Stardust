package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**  This fun little subsytems moves balls from the convery belt to the shooter flywheel for shooting*/
//or it used to.
//actually it never did.
public class Elevator {
    private final TalonSRX Elevator;

    public Elevator(final int ElevatorID) {
        Elevator = new TalonSRX(ElevatorID);
    }

    public void runElevatorVelocity(double speed) {
        Elevator.set(ControlMode.Velocity, speed * 300);
    }

    public void runElevatorPower(double power) {
        Elevator.set(ControlMode.PercentOutput, power);
    }
}