package frc.automatic.actions.zeroSensors;

import frc.automatic.runners.Actionable;
import frc.robot.Robot;

public class ZeroEncoder implements Actionable{

    public ZeroEncoder() {}

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
        return Robot.drive.getLeftDistance() == 0;
    }
}