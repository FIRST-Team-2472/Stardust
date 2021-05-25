package frc.automatic.actions.shooting;

import frc.automatic.runners.Actionable;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StartShooting implements Actionable {

    public StartShooting() {
    }

    @Override
    public void startAction() {
    }

    @Override
    public void periodic() {
        Robot.collector.runConveyorPower(-0.5);


        
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