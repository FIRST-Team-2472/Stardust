package frc.actions;


import frc.actions.runners.TimerBase;
import frc.robot.Robot;

//sawyers a nerd

public class FeedBall extends TimerBase {



	public FeedBall(double lifetime, double givenlifetime) {
        super(lifetime);
        lifetime = givenlifetime;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void startAction() {
        super.startAction();
        Robot.indexer.runIndexer(1);
    }


	@Override
	public void periodic() {
        Robot.indexer.runIndexer(1);
	}

	@Override
	public void endAction() {
        Robot.indexer.runIndexer(0);
	}

}