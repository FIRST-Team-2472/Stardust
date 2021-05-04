package frc.automatic.actions.drivestraight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.Actionable;
import frc.robot.Robot;
import frc.robot.random.Constants;

public class Correction implements Actionable {

    public double feet, sped, correction, currentEncoder;
    
    public Correction(double x) {
        feet = x;
        currentEncoder = Robot.drive.getLeftDistance();

        if (currentEncoder > feet*Constants.pulsesPerFoot) sped = -.2;
        else if (currentEncoder < feet*Constants.pulsesPerFoot) sped = .2;
        else sped = 0;
    }


    @Override
    public void startAction() {
        Robot.drive.setDistance(0, 0);
        SmartDashboard.putString("ActionName", "Drive Straight Velocity");
        Robot.drive.tankDriveVelocity(sped, sped);
    }

    @Override
    public void periodic() { 
        correction = ((Robot.drive.getLeftDistance()-Robot.drive.getRightDistance())/Robot.drive.getRightDistance())*0.005;
        Robot.drive.tankDriveVelocity(sped, sped+correction);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (currentEncoder > feet*Constants.pulsesPerFoot) {
            return Robot.drive.getLeftDistance() <=  (feet * Constants.pulsesPerFoot);
        } else if (currentEncoder < feet*Constants.pulsesPerFoot) {
            return Robot.drive.getLeftDistance() >=  (feet * Constants.pulsesPerFoot);
        } else return true;
    } 
}