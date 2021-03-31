package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


/**  This fun little subsytems moves balls from the convery belt to the shooter flywheel for shooting*/
//or it used to.
//actually it never did.
public class Elevator {
    private TalonSRX Elevator;

    public Elevator(int ElevatorID) {
        Elevator = new TalonSRX(ElevatorID);
    }

    public void runElevator(double speed) {
        Elevator.set(ControlMode.Velocity, speed * 300);
    }

}