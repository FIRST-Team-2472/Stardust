package frc.actions;

import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class TurnLeft implements Actionable {

	@Override
	public void startAction() {
        Robot.imu.reset();
    }

	@Override
	public void periodic() {
        // Robot.motors.spin
        Robot.drive.tankDrive(-0.5, 0.5);
        
        
	}

	@Override
	public void endAction() {
		Robot.drive.tankDrive(0, 0);
	}

	@Override
	public boolean isFinished() {
        /*if (Robot.imu.getAngle() > 269 && Robot.imu.getAngle() < 271)  {
          return true;
               
        }
        else {
            return false;
        }*/
        if (Robot.imu.getAngle() < -90) {
            return false;
        } else {
            return true;
        }
        /*
            if imu at the right degreee
                return true;
            else
                return false;
        */
    }
}

