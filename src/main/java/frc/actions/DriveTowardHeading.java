package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class DriveTowardHeading implements Actionable {

    public double rightspeed, leftspeed, heading, distance;
    public double kP = 0.0019;

    public DriveTowardHeading(double lleftspeed, double rrightspeed, double hheading) {
        heading = hheading;
        leftspeed = lleftspeed;
        rightspeed = rrightspeed;
        heading = Math.abs(heading)-Math.abs(Robot.drive.getCurrentAngle());
    }

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "Drive Toward Heading");
    }

    @Override
    public void periodic() { 
        Robot.drive.tankDriveVelocity(leftspeed, rightspeed-kP*(heading-(Math.abs(heading)-Math.abs(Robot.drive.getCurrentAngle()))));
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