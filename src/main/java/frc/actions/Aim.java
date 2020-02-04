package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Aim implements Actionable {

	@Override
	public void startAction() {
		SmartDashboard.putString("ActionName", "Looking for gamers");
    }

	@Override
	public void periodic() {
		SmartDashboard.putNumber("angleoffcenter", Robot.limelight.targetXAngleFromCenter());
		if (Robot.limelight.targetXAngleFromCenter() < 0) {
		Robot.turret.runTurret(-0.5); 

		} else {
		Robot.turret.runTurret(0.5);	
		}
        
	}

	@Override
	public void endAction() {
		Robot.turret.runTurret(0);
	}

	@Override
	public boolean isFinished() {
		return  Math.abs(Robot.limelight.targetXAngleFromCenter()) < 2;
    }
}