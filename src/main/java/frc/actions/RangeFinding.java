package frc.actions;

import javax.security.sasl.SaslException;

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
        SmartDashboard.putNumber("DistanceFromFiring", currentDistance);
    }

    @Override
    public void periodic() {
        if (currentDistance < 96) {
            Robot.drive.tankDriveVelocity(0.3, 0.3);
        }
        else if (currentDistance > 120) {
            Robot.drive.tankDriveVelocity(-0.3, -0.3);
        }
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
    if (currentDistance < 120 && currentDistance > 96 && Robot.limelight.isTargetSpotted() == true)
        return true;
    else 
        return false;
    }

}
