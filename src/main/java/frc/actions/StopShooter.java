package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

//sawyers a nerd

public class StopShooter extends TimerBase {



	public StopShooter(double lifetime) {
        super(lifetime);
        // TODO Auto-generated constructor stub
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