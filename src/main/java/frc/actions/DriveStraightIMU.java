package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class DriveStraightIMU implements Actionable {

    public double feet;
    public DriveStraightIMU(double x){
        this.feet = x;
    }

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        Robot.drive.zeroIMU();
        //Robot.drive.setDistance(lDis, rDis);
        Robot.drive.tankDriveVelocity(1, 1);
        }

    @Override
    public void periodic() { 
       // SmartDashboard.putString("DistanceDriven", feet);
    }

    @Override
    public void endAction() {
    }

    @Override
    public boolean isFinished() {
        if (Robot.drive.leftDriveError() < 500);
            Robot.drive.tankDriveVelocity(0, 0);
        //return Robot.drive.rightDriveError() < 500;
        return false;
    }
}

