package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Constants;
import frc.robot.Robot;

public class DriveTowardHeading implements Actionable {

    public double finalangle;
    public double leftspeed = .3;
    public double rightspeed = .3;

    public DriveTowardHeading(double left, double right, double heading){
        leftspeed = left;
        rightspeed = right;
        finalangle = heading;
    }

    @Override
    public void startAction() {
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
                return Math.abs(finalangle-Robot.drive.getCurrentAngle()) < 2;
        }
    }



