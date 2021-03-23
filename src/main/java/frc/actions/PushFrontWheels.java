package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class PushFrontWheels extends TimerBase {

    public PushFrontWheels() {
        super(0.2);    
    }

    @Override
    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "PushFrontWheels");
    }

    @Override
    public void periodic() {
        Robot.collector.pushoutfrontwheel();
    }

    @Override
    public void endAction() {
        Robot.collector.pushofffrontwheel();
    }

    //TODO May not work
}