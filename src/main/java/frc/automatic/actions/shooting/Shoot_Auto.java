package frc.automatic.actions.shooting;

import frc.automatic.runners.TimerBase;
import frc.robot.Robot;

public class Shoot_Auto extends TimerBase{

    public Shoot_Auto(double lifetime) {
        super(lifetime);
    }

    @Override
    public void startAction() {
        Robot.collector.runConveyorPower(-0.5);
        Robot.elevator.runElevatorPower(0.5);
    }

    @Override
    public void periodic() {
    }

    @Override
    public void endAction() {
        Robot.shooter.runFlyWheelPower(0);
        Robot.collector.runConveyorPower(0);
        Robot.elevator.runElevatorPower(0);
    }
}