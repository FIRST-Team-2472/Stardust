package frc.actions;

import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class StartFlyWheel extends TimerBase{

    public StartFlyWheel(double lifetime) {
        super(lifetime);
    }

    @Override
    public void startAction() {
        super.startAction();
        Robot.shooter.runFlyWheel(1);
    }

    @Override
    public void periodic() {}

    @Override
    public void endAction() {}
}