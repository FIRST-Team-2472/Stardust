package frc.automatic.actions.shooting;

import frc.automatic.runners.Actionable;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AimHorizontally implements Actionable {

	@Override
	public void startAction() {
		SmartDashboard.putString("ActionName", "Aiming Horizontally");
	}
	
	private double TurretSpeed = .1;

	@Override
	public void periodic() {
		if (Robot.limelight.targetXAngleFromCenter() > 0) {
			Robot.turret.runTurret(-TurretSpeed);
		}
		else if (Robot.limelight.targetXAngleFromCenter() < 0) {
			Robot.turret.runTurret(TurretSpeed);
		}
	}

	@Override
	public void endAction() {
		Robot.turret.runTurret(0);
	}

	@Override
	public boolean isFinished() {
		if (Robot.limelight.isTargetSpotted()) return Math.abs(Robot.limelight.targetXAngleFromCenter()) < 2;
		else return true;
    }
}