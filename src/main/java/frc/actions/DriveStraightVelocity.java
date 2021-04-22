package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.subdivison.*;
import frc.robot.Robot;

public class DriveStraightVelocity implements Actionable {

    public double feet;
    public double speed = .4;
    public double correction;
    public DriveStraightVelocity(double x){
        feet = x;
    }

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        Robot.drive.setDistance(0, 0);
        SmartDashboard.putString("ActionName", "Drive Straight Velocity");
        Robot.drive.tankDriveVelocity(.5, .5);
        }

    @Override
    public void periodic() { 
        correction = (((Robot.drive.getLeftDistance()-Robot.drive.getRightDistance())/Robot.drive.getRightDistance())*0.005);
        SmartDashboard.putNumber("Edit Speed", correction);

        if (feet > 0) Robot.drive.tankDriveVelocity(speed, speed+correction);
        else if (feet < 0) Robot.drive.tankDriveVelocity(-speed, -speed-correction);
        else Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (feet > 0) {
            return Robot.drive.getLeftDistance() >  (feet * Constants.pulsesPerFoot);
        } else if (feet == 0) {
            return true;
        } else  {   
            return Robot.drive.getLeftDistance() <  (feet * Constants.pulsesPerFoot);
        }
    } 
}

