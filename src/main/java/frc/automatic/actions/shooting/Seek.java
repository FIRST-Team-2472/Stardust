package frc.automatic.actions.shooting;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.Actionable;
import frc.robot.Robot;

public class Seek implements Actionable {

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "Seek");
    }


    @Override
    public void periodic() {
        if (Robot.limelight.isTargetSpotted() == false) {
            Robot.drive.tankDriveVelocity(-0.2, 0.2);
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
