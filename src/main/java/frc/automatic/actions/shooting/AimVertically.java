
package frc.automatic.actions.shooting;

import frc.automatic.runners.Actionable;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AimVertically implements Actionable {
    private int wantedShieldHeight = 0;

	@Override
	public void startAction() {
        SmartDashboard.putString("ActionName", "Aiming verticaly");
		wantedShieldHeight = 0;     //where formal goes
    }
    
	

	@Override
	public void periodic() {
        if (Robot.shield.getShieldHeight() > wantedShieldHeight){
            Robot.shield.runShieldPower(-0.2); 

        } else {
            Robot.shield.runShieldPower(0.2); 

        }
	}

	@Override
	public void endAction() {
        Robot.shield.runShieldPower(0);
	}

	@Override
	public boolean isFinished() {
        if (Robot.limelight.isTargetSpotted()) {
        return Math.abs(Robot.shield.getShieldHeight() - wantedShieldHeight) < 100;
        } else return true;
    }
}
    

