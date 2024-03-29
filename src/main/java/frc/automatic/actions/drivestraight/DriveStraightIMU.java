package frc.automatic.actions.drivestraight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.Actionable;
import frc.robot.Robot;
import frc.robot.random.*;

public class DriveStraightIMU implements Actionable {

    public double feet;
    public double leftspeed = .3;
    public double rightspeed = .3;
    public double heading;

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
        }

    @Override
    public void periodic() { 
        double correction;
        correction = (heading-Robot.drive.getCurrentAngle())*0.0005;
        //rightspeed = rightspeed + correction;
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

