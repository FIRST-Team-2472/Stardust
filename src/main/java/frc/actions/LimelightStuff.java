package frc.actions;

import javax.swing.Action;

import frc.actions.runners.Actionable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

//WARNING: THIS IS WES JUST MESSING WITH LIMELIGHT CODE. DO NOT TAKE THIS SERIOUSLY UNLESS IT SOMEHOW WORKS.
public class LimelightStuff implements Actionable {

    private double currentDistance = Robot.limelight.distanceIN();
    private double distanceErrorForward = 96-Robot.limelight.distanceIN();
    private double distanceErrorBackward = 120+Robot.limelight.distanceIN();
    private double KpAim = -0.1;

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "limelight stuff");
    }

    @Override
    public void periodic() {
        if (distanceErrorForward < 0 && distanceErrorBackward < 216) {
            Robot.drive.tankDriveVelocity(-0.3, -0.3);
        }
        else if (distanceErrorBackward > 240 && distanceErrorForward > -24) {
            Robot.drive.tankDriveVelocity(0.3, 0.3);
        }
        if (Robot.limelight.targetXAngleFromCenter() > 0) {
            Robot.turret.runTurret(KpAim*Robot.limelight.targetXAngleFromCenter());
        }
        else if (Robot.limelight.targetXAngleFromCenter() < 0) {
            Robot.turret.runTurret(-KpAim*Robot.limelight.targetXAngleFromCenter());
        }
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
        Robot.turret.runTurret(0);
    }

    @Override
    public boolean isFinished() {
        if (currentDistance < 120 && currentDistance > 96 && Robot.limelight.isTargetSpotted() == true && Robot.limelight.targetXAngleFromCenter() < 0.5)
        return true;
    else 
        return false;
    }
}
