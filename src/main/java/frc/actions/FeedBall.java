package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

//sawyers a nerd

public class FeedBall extends TimerBase {

    public FeedBall(double lifetime) {
        super(lifetime);
    }

    @Override
    public void startAction() {
        super.startAction();
        Robot.indexer.runIndexerOff();
        SmartDashboard.putString("ActionName", "FeedBall");
    }


    @Override
    public void periodic() {    
        Robot.indexer.runIndexerForward();;
    }

    @Override
    public void endAction() {
        Robot.indexer.runIndexerBackward();
    }

}