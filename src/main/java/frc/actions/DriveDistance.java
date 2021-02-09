package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class DriveDistance implements Actionable {

    private int distance;

    private static final double countsPerFoot = 5215;


    public DriveDistance() {
        
        this.distance = distance;
    }

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "DriveDistance");
        distance = Robot.drive.getRightDistance() + 1000000;
        Robot.drive.tankDriveMotionMagic(1000000, 1000000);
    }

    @Override
    public void periodic() {
       // SmartDashboard.putString("DistanceDriven", feet);
    }

    @Override
    public void endAction() {
        //Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
        //return distance <= Robot.drive.getRightDistance();
        //return Robot.drive.rightDriveError() < 500;
    }
}
