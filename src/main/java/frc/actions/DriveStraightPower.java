package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Constants;
import frc.robot.Robot;

public class DriveStraightPower implements Actionable{ 
    public double feet;
    public double leftspeed = .3;
    public double rightspeed = .3;

    public DriveStraightPower(double x){
        feet = x;
    }

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        Robot.drive.setDistance(0, 0);
        SmartDashboard.putString("ActionName", "Drive Straight Power");
        Robot.drive.tankDrivePower(leftspeed, rightspeed);
        }

    @Override
    public void periodic() {
        double correction; 
        if (Robot.drive.getRightDistance() != 0) {
            correction = ((Robot.drive.getLeftDistance()-Robot.drive.getRightDistance())/Robot.drive.getRightDistance())*0.005;
            rightspeed += correction;
            System.out.println(rightspeed);
            Robot.drive.tankDriveVelocity(leftspeed, rightspeed);
        }
    }

    @Override
    public void endAction() {
        Robot.drive.tankDrivePower(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Robot.drive.getLeftDistance() > (int) (feet * Constants.pulsesPerFoot);
    }
}