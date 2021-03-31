package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class DriveTowardHeading implements Actionable {

    public double rightspeed, leftspeed, heading, distance, editSpeed, smallEditSpeed;
    public double kP = 0.019;

    public DriveTowardHeading(double lleftspeed, double rrightspeed, double hheading) {
        heading = Math.abs(hheading);
        leftspeed = lleftspeed;
        rightspeed = rrightspeed;
        Robot.drive.zeroIMU();
        //heading = Math.abs(heading)-Math.abs(Robot.drive.getCurrentAngle());
    }

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "Drive Toward Heading");
    }

    @Override
    public void periodic() { 
        editSpeed = kP*(heading-(Math.abs(Math.abs(heading)-Math.abs(Robot.drive.getCurrentAngle()))))*(Math.abs(Math.abs(rightspeed)-Math.abs(leftspeed)));
        SmartDashboard.putNumber("Edit Speed", editSpeed);
        smallEditSpeed = editSpeed/2;
    
        if (leftspeed < rightspeed) {
            if (rightspeed-editSpeed < 0) editSpeed == 0;
            Robot.drive.tankDriveVelocity(leftspeed-smallEditSpeed, rightspeed-editSpeed);
        } else if (leftspeed > rightspeed) {
            Robot.drive.tankDriveVelocity(leftspeed-editSpeed, rightspeed-smallEditSpeed);
        } else {
            Robot.drive.tankDriveVelocity(leftspeed, rightspeed);
        }
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
            if (leftspeed < rightspeed) return heading < Robot.drive.getCurrentAngle();
            else if (leftspeed > rightspeed) return heading < -1*Robot.drive.getCurrentAngle();
            else return true;
        }
    }