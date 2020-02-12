package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Robot;
import frc.robot.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Aim implements Actionable {

	@Override
	public void startAction() {
		SmartDashboard.putString("ActionName", "Looking for gamers");
	}
	
	private static final double kP = .03;

	// this should not be static in case you need to aim multiple times
	private double TurretSpeed = 0;

	private Timer timeout = null;
	@Override
	public void periodic() {
		//double error = Robot.limelight.targetXAngleFromCenter();
		//Robot.turret.runTurret(kP*error);
		SmartDashboard.putNumber("Error", Robot.limelight.targetXAngleFromCenter());
		if (Robot.limelight.isTargetSpotted()) {
			TurretSpeed = Robot.limelight.targetXAngleFromCenter() * kP; 
						
			Robot.turret.runTurret (TurretSpeed);
			timeout = null;
		} else { 
			if (timeout == null) timeout = new Timer(2);
			Robot.turret.runTurret(0);
		}
        
	}

	@Override
	public void endAction() {
		Robot.turret.runTurret(0);
	}

	@Override
	public boolean isFinished() {
		return Timer.tryIsTimedOut(timeout) || Robot.limelight.isTargetSpotted() && Math.abs(Robot.limelight.targetXAngleFromCenter()) < 2;
    }

	public static void aim() {
	}
}