package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class DriveTowardHeadingRegression implements Actionable {

    public double rightspeed, leftspeed, heading, distance;

    public DriveTowardHeadingRegression(double ddistance, double hheading) {
        heading = hheading;
        distance = ddistance;
    }

    @Override
    public void startAction() {

        
        if (heading > 0) {
            leftspeed = (0.804195*heading-distance)/-24.5481;
            rightspeed = (0.77561*heading-distance)/-284.9378;
            Robot.drive.tankDriveVelocity(leftspeed,rightspeed);
        } else if (heading == 0) {
            leftspeed = 0;
            rightspeed = 0;
            Robot.drive.tankDriveVelocity(leftspeed,rightspeed);
        } else {
            leftspeed = (-0.0697*heading-distance)/-421.854;
            rightspeed = (0.593954*heading-distance)/-550.6227;
            Robot.drive.tankDriveVelocity(leftspeed,rightspeed);
        }

        Robot.drive.tankDriveVelocity(leftspeed, rightspeed);
        SmartDashboard.putString("ActionName", "Drive Toward Heading");
        }

    @Override
    public void periodic() { 

    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
                return Math.abs(Math.abs(heading)-Math.abs(Robot.drive.getCurrentAngle())) < 1;
        }
    }