package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/** System to make the robot climb */
public class Climber {
    private TalonSRX climber;

    /**
     * Constructs the climer
     * @param ClimberID Id for the talonSRX connected to climber motor
     */
    public Climber (int ClimberID) {
        climber = new TalonSRX(ClimberID);
    }

    /**
     * Runs the motor to move the climber arm
     * @param speed [-1.0, 1.0] value to run motor
     */
    public void runClimber(double speed){
        climber.set(ControlMode.PercentOutput, speed);
    }
}