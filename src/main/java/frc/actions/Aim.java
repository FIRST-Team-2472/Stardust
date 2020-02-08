package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Aim implements Actionable {

	@Override
	public void startAction() {
		SmartDashboard.putString("ActionName", "Looking for gamers");
	}
	
	private static final double kP = .03;
	private static double TurretSpeed = 0;
	@Override
	public void periodic() {
		//double error = Robot.limelight.targetXAngleFromCenter();
		//Robot.turret.runTurret(kP*error);
		SmartDashboard.putNumber("Error", Robot.limelight.targetXAngleFromCenter());
		SmartDashboard.putBoolean("seein it", Robot.limelight.isTargetSpotted());
		if (Robot.limelight.isTargetSpotted()) {
			if (Robot.limelight.targetXAngleFromCenter() < 0) {
				TurretSpeed = Robot.limelight.targetXAngleFromCenter() * kP; 
			} else {
				TurretSpeed =  Robot.limelight.targetXAngleFromCenter() * kP;	
			}
			Robot.turret.runTurret (TurretSpeed);
		} else {
			Robot.turret.runTurret(0);
		}
        
	}

	@Override
	public void endAction() {
		Robot.turret.runTurret(0);
	}

	@Override
	public boolean isFinished() {
		return  Robot.limelight.isTargetSpotted()  && Math.abs(Robot.limelight.targetXAngleFromCenter()) < 2;
	
    }
}