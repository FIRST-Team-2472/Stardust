package frc.actions;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class RangeFinding implements Actionable {

    private double currentDistance = Robot.limelight.distanceIN();
    private double distanceErrorForward = 96-Robot.limelight.distanceIN();
    private double distanceErrorBackward = 120+Robot.limelight.distanceIN();

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "RangeFinding");
    }

    @Override
    public void periodic() {
        if (distanceErrorForward < 0 && distanceErrorBackward < 216) {
            Robot.drive.tankDriveVelocity(-0.3, -0.3);
        }
        else if (distanceErrorBackward > 240 && distanceErrorForward > -24) {
            Robot.drive.tankDriveVelocity(0.3, 0.3);
        }
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
     if (currentDistance < 120 && currentDistance > 96)
        return true;
    else 
        return false;
    }
}
