package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;


public class TurnRobot implements Actionable {
    private final int angle;

    
    public TurnRobot(int angle){
        this.angle = angle;
    }
    

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        Robot.drive.zeroIMU();
        Robot.drive.setDistance(0, 0);
        SmartDashboard.putString("ActionName", "Turn");
    }

    @Override
    public void periodic() {
        if (angle < 0) Robot.drive.tankDriveVelocity(-.15, .15);
        if (angle > 0) Robot.drive.tankDriveVelocity(.15,-.15);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (angle > 0) {
            return Robot.drive.getCurrentAngle() > angle;
        } else if (angle == 0) {
            return true;
        } else {
            return Robot.drive.getCurrentAngle() < angle;
        }
    }
}