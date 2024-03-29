package frc.automatic.actions.shooting;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.Actionable;
import frc.robot.Robot;

public class ShieldAim implements Actionable{
    public double speed = -.3;
    public double distance;
    public double kP = 0;

    public ShieldAim() {}

    @Override
	public void startAction() {
        SmartDashboard.putString("ActionName", "Sheild Aim");
        distance = Robot.limelight.get_distance_in(); //+ regression model
    }

	@Override
	public void periodic() {
        speed = distance - ((distance-Robot.shield.getShieldHeight())*kP);
        Robot.shield.runShieldPower(speed);
	}

	@Override
	public void endAction() {
        Robot.shield.runShieldPower(0);
	}

	@Override
	public boolean isFinished() {
        if (Robot.shield.getShieldHeight() < distance) return true;
        else return false;
    }
}