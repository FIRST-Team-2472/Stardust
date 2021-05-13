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
        
       
    }

    @Override
    public void periodic() {
        Robot.shooter.runFlyWheelPower(-1);
        Robot.collector.runConveyorPower(-0.5);
        Robot.elevator.runElevatorPower(0.5);
        
    }

    @Override
    public void endAction() {
        Robot.shooter.runFlyWheelPower(0);
        Robot.collector.runConveyorPower(0);
        Robot.elevator.runElevatorPower(0);
    }

}