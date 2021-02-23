package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;


public class TurnRobot implements Actionable {
    private double angle;

    
    public TurnRobot(double Angle){
        angle = Angle;
    }
    

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        Robot.drive.zeroIMU();
        SmartDashboard.putString("ActionName", "Turn");
    }

    @Override
    public void periodic() {
        if (angle-Robot.drive.getCurrentAngle() > 0) Robot.drive.tankDriveVelocity(-.20, .20);
        if (angle-Robot.drive.getCurrentAngle() < 0) Robot.drive.tankDriveVelocity(.20,-.20);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {

        if (Math.abs(angle-Robot.drive.getCurrentAngle()) < 6) {
            return true;
        }
        return false;
    }
}