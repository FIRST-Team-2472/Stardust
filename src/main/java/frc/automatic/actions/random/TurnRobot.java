package frc.automatic.actions.random;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.Actionable;
import frc.robot.Robot;


public class TurnRobot implements Actionable {
    private double angle;

    
    public TurnRobot(double Angle){
        angle = Angle;
    }
    

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "Turn");
        //Robot.drive.zeroIMU();
        
    }

    @Override
    public void periodic() {
        if (angle-Robot.drive.getCurrentAngle() > 0) Robot.drive.tankDrivePower(.2, -.2);
        if (angle-Robot.drive.getCurrentAngle() < 0) Robot.drive.tankDrivePower(-.2, .2);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
		if (Math.abs(angle-Robot.drive.getCurrentAngle()) < 2.0) {
            return true;
        }
        return false;
    }
}