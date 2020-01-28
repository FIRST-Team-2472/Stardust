package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Limelight;
import frc.robot.Robot;
import frc.subsystems.Turret;
import edu.wpi.first.networktables.NetworkTableInstance;


public class Aim implements Actionable {

	@Override
	public void startAction() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry( "tx" ).getDouble(0);
    }

	@Override
	public void periodic() {
         if (Robot.limelight.distanceAngle(){
         }
	}

	@Override
	public void endAction() {
	
	}

	@Override
	public boolean isFinished() {
     
    }
}

