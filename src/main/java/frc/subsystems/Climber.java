package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/** System to make the robot climb */
public class Climber {
    private TalonSRX climber;

    public Climber (int ClimberID) {
        climber = new TalonSRX(ClimberID);
    }

    public void runClimber(double speed){
        climber.set(ControlMode.PercentOutput, speed);
    }
}