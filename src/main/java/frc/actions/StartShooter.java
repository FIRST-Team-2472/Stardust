package frc.actions;

import frc.actions.runners.TimerBase;
import frc.robot.Robot;

//sawyers a nerd

public class StartShooter extends TimerBase {



	public StartShooter(double lifetime) {
        super(lifetime);
        // TODO Auto-generated constructor stub
    }


	@Override
	public void periodic() {
        Robot.shooter.runFlyWheel(1);
	}

	@Override
	public void endAction() {

	}

}