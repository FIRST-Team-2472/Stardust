package frc.automatic.actions.extras;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.Actionable;
import frc.robot.Robot;


public class TurnToHeading implements Actionable {
    private double angle;
    public double leftspeed, rightspeed; 
    public double kF = 0.35;
    public double kP = 0.05;
    double leftBaseSpeed = leftspeed * kF;
    double rightBaseSpeed = rightspeed * kF;


    
    public TurnToHeading(double Left, double Right, double Angle){
        angle = Angle;
        leftspeed = Left;
        rightspeed = Right;
    }
    

    @Override
    public void startAction() {
        Robot.drive.tankDriveVelocity(leftspeed, rightspeed);
        Robot.drive.zeroIMU();
        SmartDashboard.putString("ActionName", "TurnHeading");
    }

    @Override
    public void periodic() {
        Robot.drive.tankDriveVelocity(Math.min(leftspeed, leftBaseSpeed+Math.abs(angle-Robot.drive.getCurrentAngle())*kP), Math.min(rightspeed, rightBaseSpeed+Math.abs(angle-Robot.drive.getCurrentAngle())*kP));
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
		if (angle-Robot.drive.getCurrentAngle() < 2) {
            return true;
        }
        return false;
    }
}