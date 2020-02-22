package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/** System to make the robot climb */
public class Climber {
    private TalonSRX climber;



    /**
     * Sets the id
     * @param ClimberRID This is the canbus id for the motor
     */
    public Climber (int ClimberRID) {
        climber = new TalonSRX(ClimberRID);
       
    }
    /**
     * 
     * @param speed
     */
    public void runClimber(double speed){
        climber.set(ControlMode.PercentOutput, speed);
    }

}