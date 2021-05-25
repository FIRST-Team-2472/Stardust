package frc.automatic.actions.shooting;

import frc.automatic.runners.TimerBase;
import frc.robot.Robot;
import frc.subsystems.Elevator;

public class StartElevator extends TimerBase {
    
    public StartElevator(double lifetime) {
        super(lifetime);
    }

    @Override
    public void startAction() {
        super.startAction();
        Robot.elevator.runElevatorPower(0.5);
       
    }

    @Override
    public void periodic() {
    }

    @Override
    public void endAction() {
    }
}
