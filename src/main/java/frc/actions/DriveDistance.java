package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class DriveDistance implements Actionable {

    private final double feet;

    private static final double countsPerFoot = 5215;


    public DriveDistance(double feet) {
        this.feet = feet;
    }

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "DriveDistance");
    }

    @Override
    public void periodic() {
        Robot.drive.driverFeet(feet);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Robot.drive.driveError() < 500;
    }
}
