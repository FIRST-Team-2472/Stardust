package frc.actions;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Constants;
import frc.robot.Robot;

public class DriveStraightPower implements Actionable{ 
    public double feet;
    public DriveStraightPower(double x){
        feet = x;
    }

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        //Robot.drive.setDistance(lDis, rDis);
        Robot.drive.tankDrivePower(-.3, -.3);
        }

    @Override
    public void periodic() { 
       // SmartDashboard.putString("DistanceDriven", feet);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDrivePower(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (Robot.drive.getLeftDistance() > feet * Constants.pulsesPerFoot)
            return true;
 
        //return Robot.drive.rightDriveError() < 500;
        return false;
    }
}