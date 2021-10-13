package frc.automatic.actions.extras;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.Actionable;
import frc.robot.Robot;

public class SetShield implements Actionable {

	public SetShield() {
    }

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "Set Shield");
        Robot.shield.zeroCounters();
    }

    @Override
    public void periodic() {
        Robot.shield.runShieldPower(-0.2);


        
    }

    @Override
    public void endAction() {
        Robot.shield.runShieldPower(0);
        Robot.shield.zeroCounters();
    }
    
    @Override
    public boolean isFinished() {
        return(Robot.shield.getLimitSwitchOn());
        //return false;
    }
}