package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Robot;
import frc.robot.Constants;

public class DriveStraightVelocity implements Actionable {

    public double feet;
    public DriveStraightVelocity(double x){
        feet = x;
    }

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        //Robot.drive.setDistance(lDis, rDis);
        Robot.drive.tankDriveVelocity(.3, .3);
        }

    @Override
    public void periodic() { 
       // SmartDashboard.putString("DistanceDriven", feet);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (Robot.drive.getLeftDistance() > feet * Constants.pulsesPerFoot)
        return true;

    //return Robot.drive.rightDriveError() < 500;
    return false;
    }
}

