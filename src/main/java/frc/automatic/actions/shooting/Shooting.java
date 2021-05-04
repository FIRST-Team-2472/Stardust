package frc.automatic.actions.shooting;

import frc.automatic.runners.TimerBase;
import frc.robot.Robot;

public class Shooting extends TimerBase {

    public Shooting(double lifetime) {
        super(lifetime);
    }

    @Override
    public void startAction() {
        super.startAction();
        
        Robot.shooter.runFlyWheel(1);
        Robot.collector.runConveyor(0.5);
        Robot.elevator.runElevatorPower(0.5);
    }

    @Override
    public void periodic() {
        
    }

    @Override
    public void endAction() {
        Robot.shooter.runFlyWheel(0);
        Robot.collector.runConveyor(0);
        Robot.elevator.runElevatorPower(0);
    }

}