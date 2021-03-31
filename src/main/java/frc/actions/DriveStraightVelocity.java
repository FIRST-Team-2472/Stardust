package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Constants;
import frc.robot.Robot;

public class DriveStraightVelocity implements Actionable {

    public double feet;
    public DriveStraightVelocity(double x){
        feet = x;
    }

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        Robot.drive.setDistance(0, 0);
        SmartDashboard.putString("ActionName", "Drive Straight Velocity");
        Robot.drive.tankDriveVelocity(.5, .5);
        }

    @Override
    public void periodic() { 
        Robot.drive.tankDriveVelocity(.5, .5+(((Robot.drive.getLeftDistance()-Robot.drive.getRightDistance())/Robot.drive.getRightDistance())*0.005));
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
        } else  {   
            return Robot.drive.getLeftDistance() < (int) (feet * Constants.pulsesPerFoot);
        }
    } 
}

