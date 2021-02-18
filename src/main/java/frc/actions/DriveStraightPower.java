package frc.actions;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Constants;
import frc.robot.Robot;

public class DriveStraightPower implements Actionable{ 
    public double feet;
    public DriveStraightPower(double x){
        this.feet = x;
    }

    @Override
    public void startAction() {
        Robot.drive.zeroCounters();
        Robot.drive.setDistance(0, 0);
        Robot.drive.tankDrivePower(0.5, 0.5);
        }

    @Override
    public void periodic() { 
       // SmartDashboard.putString("DistanceDriven", feet);
    }

    @Override
    public void endAction() {
    }

    @Override
    public boolean isFinished() {
        if (Robot.drive.getLeftDistance() > (int)(feet * Constants.pulsesPerFoot)) {
            return true;
        }
        
        //return Robot.drive.rightDriveError() < 500;
        return false;
    }
}