package frc.automatic.actions.driveStraight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.Actionable;
import frc.robot.Robot;
import frc.robot.random.Constants;

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
        if (feet > 0) {
            return Robot.drive.getLeftDistance() > (int) (feet * Constants.pulsesPerFoot);
        } else if (feet == 0) {
            return true;
        } else {   
            return Robot.drive.getLeftDistance() < (int) (feet * Constants.pulsesPerFoot);
        }
    }
}