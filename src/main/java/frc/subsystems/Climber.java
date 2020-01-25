package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber {
    private TalonSRX ClimberR;
    private TalonSRX ClimberL;


   public Climber (int ClimberRID,int ClimberLID) {
        ClimberR = new TalonSRX(ClimberRID);
        ClimberL = new TalonSRX(ClimberLID);
   }

   public void runClimber(double speed){
       runClimberL(speed);
       runClimberR(speed);
   }

   public void runClimberR(double speed) {
       ClimberR.set(ControlMode.PercentOutput, speed);
   }

   public void runClimberL(double speed) {
        ClimberL.set(ControlMode.PercentOutput, speed);
}
}