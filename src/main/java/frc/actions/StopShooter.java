package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

//sawyers a nerd

public class StopShooter extends TimerBase {



	public StopShooter() {
        super(0.1);
    }

    @Override
    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "StopShooter");
    }


	@Override
	public void periodic() {
        Robot.shooter.runFlyWheel(0);
	}

	@Override
	public void endAction() {

	}

}