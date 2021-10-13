
package frc.automatic.actions.shooting;

import frc.automatic.runners.Actionable;
import frc.robot.Robot;
import frc.robot.random.SmartDashBoard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AimVertically implements Actionable {
    private double wantedShieldHeight = 0;
    private double distance = Robot.limelight.get_distance_in();

	@Override
	public void startAction() {
        SmartDashboard.putString("ActionName", "Aiming vertical");
        distance = Robot.limelight.get_distance_in();
        //wantedShieldHeight = ((0.00089765)*(Robot.limelight.get_distance_in())) - 29.123;     //where formal goes
        wantedShieldHeight = 1100 * (Robot.limelight.get_distance_in() - 160) + 210000;
        SmartDashboard.putNumber("Wanted Shield Height", wantedShieldHeight);
    }
    
	

	@Override
	public void periodic() {
        if (Robot.shield.getShieldHeight() > wantedShieldHeight){
            Robot.shield.runShieldPower(-0.1); 

        } else {
            Robot.shield.runShieldPower(0.1); 

        }
	}

	@Override
	public void endAction() {
        Robot.shield.runShieldPower(0);
	}

	@Override
	public boolean isFinished() {
        if (Robot.limelight.isTargetSpotted()) {
        return Math.abs(Robot.shield.getShieldHeight() - wantedShieldHeight) < 200;
        } else return true;
    }
}
    

