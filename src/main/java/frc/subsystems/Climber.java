package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber {
    private TalonSRX ClimberR;
    private TalonSRX ClimberL;
    private TalonSRX PullClimberR;
    private TalonSRX PullClimberL;


    public Climber (int ClimberRID,int ClimberLID, int PullClimberLID, int PullClimberRID) {
        ClimberR = new TalonSRX(ClimberRID);
        ClimberL = new TalonSRX(ClimberLID);
        PullClimberR = new TalonSRX(PullClimberRID);
        PullClimberL = new TalonSRX(PullClimberLID);
    }

    public void runClimber(double speed){
        runClimberL(speed);
        runClimberR(speed);
    }

    // there only needs to be one run climber method / side (it is a one directional control)
    public void runClimberR(double speed) {
        ClimberR.set(ControlMode.PercentOutput, speed);
    }

    public void runClimberL(double speed) {
        ClimberL.set(ControlMode.PercentOutput, speed);
    }
    public void runPullClimberL(double speed) {
        PullClimberL.set(ControlMode.PercentOutput, speed);
    }

    public void runPullClimberR(double speed) {
        PullClimberR.set(ControlMode.PercentOutput, speed);
    }
}