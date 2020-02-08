package frc.actions;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
        SmartDashboard.putString("ActionName", "DriveDistance");
    }

    @Override
    public void periodic() {
        Robot.drive.driverMeters(target);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
            .getClosedLoopError()
    }
}
