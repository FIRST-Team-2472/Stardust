package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class DriveStraight extends TimerBase {

    public double speed;

    public DriveStraight(double givenSpeed, double seconds) {
        super(seconds);
        speed = givenSpeed;
    }

    @Override
    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "DriveStraight");
    }

    @Override
    public void periodic() {
        Robot.drive.tankDrive(speed, speed);

    }

    @Override
    public void endAction() {
        Robot.drive.tankDrive(0, 0);

    }

}