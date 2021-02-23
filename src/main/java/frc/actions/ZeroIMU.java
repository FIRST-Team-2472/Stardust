package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class ZeroIMU implements Actionable{

    public ZeroIMU() {}

    @Override
    public void startAction() {
        Robot.drive.zeroIMU();
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
