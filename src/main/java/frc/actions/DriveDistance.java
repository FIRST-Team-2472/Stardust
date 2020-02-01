package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class DriveDistance implements Actionable{

    public final double target;

    private double countsPerMeter = 99999;

    public DriveDistance(double meters) {
        target = meters * (countsPerMeter);
    }

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "DriveDiatance");
    }

    @Override
    public void periodic() {
        Robot.drive.driverMeters(target);
    }

    @Override
    public void endAction() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }



}