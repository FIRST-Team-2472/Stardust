package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Limelight;
import frc.robot.Robot;
import frc.subsystems.Turret;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Aim implements Actionable {

	@Override
	public void startAction() {
		NetworkTableInstance.getDefault().getTable("limelight").getEntry( "tx" ).getDouble(0);
		SmartDashboard.putString("ActionName", "Aim");
    }

	@Override
	public void periodic() {
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