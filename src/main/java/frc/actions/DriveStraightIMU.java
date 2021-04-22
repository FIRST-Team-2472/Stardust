package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.subdivison.*;
import frc.robot.Robot;

public class DriveStraightIMU implements Actionable {

    public double feet;
    public double leftspeed;
    public double rightspeed;
    public double heading;
    public double speed = .4;

    public DriveStraightIMU(double x, double angle){
        this.feet = x;
        heading = angle;
    }

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        Robot.drive.setDistance(0, 0);
        Robot.drive.tankDriveVelocity(leftspeed, rightspeed);
        SmartDashboard.putString("ActionName", "Drive Straight IMU");
        if (feet > 0) {
            leftspeed = speed;
            rightspeed = speed;
        }
        else if (feet < 0) {
            leftspeed = -speed;
            rightspeed = -speed;
        }
        else {
            leftspeed = 0;
            rightspeed = 0;
        }
        }

    @Override
    public void periodic() { 
        double correction;
        correction = (heading-Robot.drive.getCurrentAngle())*0.0005;
        rightspeed = rightspeed + correction;
        SmartDashboard.putNumber("Edit Speed", correction);

        Robot.drive.tankDriveVelocity(leftspeed, rightspeed);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (feet > 0) {
            return Robot.drive.getLeftDistance() > (int) (feet * Constants.pulsesPerFoot);
        } else if (feet == 0) {
            return true;
        } else {   
            return Robot.drive.getLeftDistance() < (int) (feet * Constants.pulsesPerFoot);
        }
    }
}

