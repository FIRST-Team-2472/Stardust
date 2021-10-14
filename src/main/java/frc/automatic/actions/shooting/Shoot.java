package frc.automatic.actions.shooting;

import frc.automatic.runners.Actionable;
import frc.robot.Robot;

public class Shoot implements Actionable {

    public Shoot() {
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
    
    @Override
    public boolean isFinished() {
        if(Robot.xboxcontroller.getXButtonReleased()) return true;
        else return false;
    }

}