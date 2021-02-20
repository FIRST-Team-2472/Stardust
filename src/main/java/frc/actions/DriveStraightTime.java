package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class DriveStraightTime extends TimerBase {

    public double speed;

    public DriveStraightTime(double givenSpeed, double seconds) {
        super(seconds);
        speed = givenSpeed;
    }

    @Override
    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "DriveStraight");
        Robot.drive.tankDrivePower(speed, speed);
    }

    @Override
    public void periodic() {

    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);

    }

}