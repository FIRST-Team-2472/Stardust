
package frc.automatic.actions.shooting;

import frc.automatic.runners.Actionable;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AimVertically implements Actionable {
    private double wantedShieldHeight = 0;
    private double distance = 0;

	@Override
	public void startAction() {
        SmartDashboard.putString("ActionName", "Aiming vertical");
        distance = Robot.limelight.distanceIN();
        wantedShieldHeight = (0.00089765*distance) -29.123;     //where formal goes
    }
    
	

	@Override
	public void periodic() {
        if (Robot.shield.getShieldDistance() > wantedShieldHeight){
            Robot.shield.runShieldPower(0.2); 

        } else {
            Robot.shield.runShieldPower(-0.2); 

        }
	}

	@Override
	public void endAction() {
        Robot.shield.runShieldPower(0);
	}

	@Override
	public boolean isFinished() {
        if (Robot.limelight.isTargetSpotted()) {
        return Math.abs(Robot.shield.getShieldDistance() - wantedShieldHeight) < 100;
        } else return true;
    }
}
    

