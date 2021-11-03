package frc.actions;


import frc.actions.runners.TimerBase;
import frc.robot.Robot;

//sawyers a nerd

// Run Shooter for X seconds
public class Shooting extends TimerBase {

    public Shooting(double lifetime) {
        super(lifetime);
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