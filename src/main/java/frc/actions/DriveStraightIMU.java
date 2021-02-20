package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Constants;
import frc.robot.Robot;

public class DriveStraightIMU implements Actionable {

    public double feet;
    public double leftspeed = .3;
    public double rightspeed = .3;

    public DriveStraightIMU(double x){
        this.feet = x;
    }

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        Robot.drive.zeroIMU();
        Robot.drive.setDistance(0, 0);
        Robot.drive.tankDriveVelocity(leftspeed, rightspeed);
        SmartDashboard.putString("ActionName", "Drive Straight IMU");

        }

    @Override
    public void periodic() { 
        double correction;
        correction = -Robot.drive.getCurrentAngle()*0.0005;
        rightspeed += correction;
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

