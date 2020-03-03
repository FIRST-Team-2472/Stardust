package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/** System to make the robot climb */
public class Climber {
    private TalonSRX climberR;
    private TalonSRX climberL;

    public Climber (int ClimberRID, int ClimberLID) {
        climberR = new TalonSRX(ClimberRID);
        climberL = new TalonSRX(ClimberLID);
    }

    public void runClimber(double left, double right) {
        runClimberL(left);
        runClimberR(right);
    }

    public void runClimberL(double speed) {
        climberL.set(ControlMode.PercentOutput, speed);
    }

    public void runClimberR(double speed){
        climberR.set(ControlMode.PercentOutput, speed);
    }



}