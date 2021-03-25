package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class DriveTowardHeading implements Actionable {

    public double rightspeed, leftspeed, heading, distance;

    public DriveTowardHeading(double lleftspeed, double rrightspeed, double hheading) {
        heading = hheading;
        leftspeed = lleftspeed;
        rightspeed = rrightspeed;
    }

    @Override
    public void startAction() {

        
        if (heading > 0) {
            leftspeed = (heading*1-distance-8.6)/356;
            rightspeed = (heading*1-distance+17.8)/-55;
            Robot.drive.tankDriveVelocity(1,1);
        } else if (heading == 0) {
            leftspeed = 0;
            rightspeed = 0;
        } else {

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