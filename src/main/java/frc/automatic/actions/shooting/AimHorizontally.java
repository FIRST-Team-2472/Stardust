package frc.automatic.actions.shooting;

import frc.automatic.runners.Actionable;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AimHorizontally implements Actionable {

	@Override
	public void startAction() {
		SmartDashboard.putString("ActionName", "Aiming");
	}
	
	private static final double kP = -.027;
	private double TurretSpeed = 0;

	@Override
	public void periodic() {
		if (Robot.limelight.isTargetSpotted()) {
			TurretSpeed = Robot.limelight.targetXAngleFromCenter() * kP; 
			Robot.turret.runTurret (TurretSpeed);
		}
	}

	@Override
	public void endAction() {
		Robot.turret.runTurret(0);
	}

	@Override
	public boolean isFinished() {
		if (Robot.limelight.isTargetSpotted()) return Math.abs(Robot.limelight.targetXAngleFromCenter()) < 0.5;
		else return true;
    }
}