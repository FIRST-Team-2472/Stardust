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
            Robot.drive.tankDriveVelocity(0.3, 0.3);
            
        }
    }

    @Override
    public void endAction() {
        
    }

    @Override
    public boolean isFinished() {
  
    return (Robot.limelight.isTargetSpotted());
  

}
}
