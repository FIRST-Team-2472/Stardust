package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Constants;
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
        /*
        if (heading > 0) {
            
        } else if (heading == 0) {
            leftspeed = 0;
            rightspeed = 0;
        } else {

        }*/

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
                return Math.abs(heading-Robot.drive.getCurrentAngle()) < 2;
        }
    }



