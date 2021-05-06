package frc.automatic.actions.shooting;

import frc.automatic.runners.TimerBase;
import frc.robot.Robot;

public class StartFlyWheel extends TimerBase{

    public StartFlyWheel(double lifetime) {
        super(lifetime);
    }

    @Override
    public void startAction() {
        super.startAction();
        Robot.shooter.runFlyWheelPower(1);
    }

    @Override
    public void periodic() {}

    @Override
    public void endAction() {}
}