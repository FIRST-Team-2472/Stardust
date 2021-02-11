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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
        Robot.drive.setRightDistance(0);
        Robot.drive.setLeftDistance(0);
        Robot.drive.tankDriveMotionMagic(10000, 10000);
        System.out.println("Drive Start");
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    }

    @Override
    public void periodic() {
        Robot.drive.driverFeet(feet);
       // SmartDashboard.putString("DistanceDriven", feet);
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Robot.drive.leftDriveError() < 500;
        //return Robot.drive.rightDriveError() < 500;
    }
}
