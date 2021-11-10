package frc.robot.random;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

    
public class SmartDashBoard {
    Preferences prefs;
    
    public void update() {
        
        // Pressure and solenoid SmartDashboard stuff
        // SmartDashboard.putNumber("Raw Pressure", pressure.getValue());
        SmartDashboard.putNumber("Raw Pressure", Robot.pressure.getAverageValue());
        SmartDashboard.putNumber("PSI", (Robot.pressure.getAverageValue() - 400) * (70.0 / 1250.0));

        SmartDashboard.putNumber("Angle", Robot.drive.getCurrentAngle());

        // Limelight stuff
        SmartDashboard.putNumber("x degrees off", Robot.limelight.targetXAngleFromCenter());
        SmartDashboard.putBoolean("Aimed", Math.abs(Robot.limelight.targetXAngleFromCenter()) < 1.5);
        SmartDashboard.putBoolean("seeing target?", Robot.limelight.isTargetSpotted());
        SmartDashboard.putNumber("DistanceFromFiring", Robot.limelight.get_distance_in());
        SmartDashboard.putBoolean("In Firing Range?", Robot.limelight.is_target_in_shooting_range());
        SmartDashboard.putNumber("Shield Height", Robot.shield.getShieldHeight());
    }

    public void smartdashboardTest() {
        SmartDashboard.putNumber("Left Joystick value", Robot.leftJoystick.getY());
        SmartDashboard.putNumber("Left Speed:", Robot.drive.getLeftSpeed());
        SmartDashboard.putNumber("Right Speed:", Robot.drive.getRightSpeed());
        // Distance SmartDashboard stuff
        SmartDashboard.putNumber("RightDistance", Robot.drive.getRightDistance());
        SmartDashboard.putNumber("LeftDistance", Robot.drive.getLeftDistance());
        SmartDashboard.putNumber("TurretDistance", Robot.turret.getTurretDistance());
        SmartDashboard.putNumber("Right Distance inches", Robot.drive.getRightDistance() / (Constants.pulsesPerFoot / 12));
        SmartDashboard.putNumber("Left Distance inches", Robot.drive.getLeftDistance() / (Constants.pulsesPerFoot / 12));
        SmartDashboard.getNumber("Flywheel", Robot.shooter.runSensorVelocity());
        /* stuff we don't use anymore for the moment
        // Motion Magic SmartDashboard Stuff
        SmartDashboard.putNumber("LeftError", Robot.drive.leftDriveError());
        SmartDashboard.putNumber("RightError", Robot.drive.rightDriveError());
        SmartDashboard.putNumber("RightDesired", Robot.drive.desiredRight);
        SmartDashboard.putNumber("LeftDesired", Robot.drive.desiredLeft);
        */
    }
}
