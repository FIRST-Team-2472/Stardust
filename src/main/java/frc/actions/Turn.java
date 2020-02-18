package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class Turn implements Actionable {
    private final int angle;
    
    public Turn(int angle){
        this.angle = angle;
    }
    

    @Override
    public void startAction() {
        Robot.imu.reset();
        SmartDashboard.putString("ActionName", "Turn");
    }

    @Override
    public void periodic() {
        if (angle < 0) {
            Robot.drive.tankDrive(-.5, .5);
        } else {
            Robot.drive.tankDrive(.5, -.5);
        }
    }

    @Override
    public void endAction() {
        Robot.drive.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(angle - Robot.imu.getAngle()) < 2;
    }

}