package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

//sawyers a nerd

public class StartShooter extends TimerBase {



	public StartShooter(double lifetime) {
        super(lifetime);
    }

    @Override
    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "StartShooter");
    }

	@Override
	public void periodic() {
        Robot.shooter.runFlyWheel(1);
	}

	@Override
	public void endAction() {

	}

}