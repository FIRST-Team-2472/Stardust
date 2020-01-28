package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Limelight;
import frc.robot.Robot;
import frc.subsystems.Turret;
import edu.wpi.first.networktables.NetworkTableInstance;


public class TurretAimingLeft implements Actionable {

	@Override
	public void startAction() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry( "tx" ).getDouble(0);
    }

	@Override
	public void periodic() {
        Robot.turret.runTurret(-0.5); 

        
	}

	@Override
	public void endAction() {
		Robot.turret.runTurret(0);
	}

	@Override
	public boolean isFinished() {
        if (Robot.limelight.distanceAngle() > 170){
            return false;
        } else {
            return true;
        }
    }
}