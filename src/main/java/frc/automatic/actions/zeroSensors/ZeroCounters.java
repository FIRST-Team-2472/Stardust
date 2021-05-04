package frc.automatic.actions.zerosensors;

import frc.automatic.runners.Actionable;
import frc.robot.Robot;

public class ZeroCounters implements Actionable{

    public ZeroCounters() {}

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
    }

    @Override
    public void periodic() {}

    @Override
    public void endAction() {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
