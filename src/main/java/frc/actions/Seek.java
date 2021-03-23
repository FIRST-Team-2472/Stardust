package frc.actions;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class Seek implements Actionable {

    @Override
    public void startAction() {
        
    }


    @Override
    public void periodic() {
        if (Robot.drive.getCurrentAngle() < 0) {
            Robot.drive.tankDriveVelocity(0.3, 0.1);
        }
        else if (Robot.drive.getCurrentAngle() > 0) {
            Robot.drive.tankDriveVelocity(0.1, 0.3);
        }
        
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
  
    return (Robot.limelight.isTargetSpotted());
  

}
}
