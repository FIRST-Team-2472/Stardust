package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

//sawyers a nerd
/** Action to */
public class FeedBall extends TimerBase {


    public FeedBall() {
        super(.25);    
    }

    @Override
    public void startAction() {
        super.startAction();
        Robot.indexer.runIndexerOff();
        SmartDashboard.putString("ActionName", "FeedBall");
    }

    @Override
    public void periodic() {
            Robot.indexer.runIndexerForward();
    }

    @Override
    public void endAction() {
        Robot.indexer.runIndexerBackward();
        // Hope this is engut time for the watchdog
        // We get 20ms so this should be ok
        try {Thread.sleep(10);} catch (Exception e) {};
        Robot.indexer.runIndexerOff();
        // Who said mutiang global state from antother thread was not good
        // I think it is great
        //SetTimeout.setTimeout(() -> {Robot.indexer.runIndexerOff();}, .1);
    }

}