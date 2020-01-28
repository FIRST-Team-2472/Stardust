package frc.actions;

import javax.lang.model.util.ElementScanner6;

import frc.actions.runners.Actionable;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

//sawyers a nerd

public class FeedBall extends TimerBase {



	public Shooting(double lifetime, double givenlifetime) {
        super(lifetime);
        lifetime = givenlifetime;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void startAction() {
        super.startAction();
        
    }


	@Override
	public void periodic() {
        Robot.shooter.runFlyWheel(1);
	}

	@Override
	public void endAction() {
        Robot.shooter.runFlyWheel(0);
	}

}