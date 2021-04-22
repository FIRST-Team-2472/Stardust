package frc.robot.subdivison;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

    
public class SmartDashBoard {
    Preferences prefs;
    
    public void update() {
        SmartDashboard.putNumber("Left Joystick value", Robot.leftJoystick.getY());
        SmartDashboard.putNumber("Left Speed:", Robot.drive.getLeftSpeed());
        SmartDashboard.putNumber("Right Speed:", Robot.drive.getRightSpeed());
        // SMART Dashboard perfs
        GetPrefs();
        // Distance SmartDashboard stuff
        SmartDashboard.putNumber("RightDistance", Robot.drive.getRightDistance());
        SmartDashboard.putNumber("LeftDistance", Robot.drive.getLeftDistance());
        SmartDashboard.putNumber("TurretDistance", Robot.turret.getTurretDistance());
        SmartDashboard.putNumber("Right Distance inches", Robot.drive.getRightDistance() / (Constants.pulsesPerFoot / 12));
        SmartDashboard.putNumber("Left Distance inches", Robot.drive.getLeftDistance() / (Constants.pulsesPerFoot / 12));
        SmartDashboard.getNumber("Flywheel", Robot.shooter.runSensorVelocity());
        // Robot state SmartDashboard stuff
        SmartDashboard.putString("RobotState", "TeleopEnabled");
        SmartDashboard.putString("RobotState", "Robot On");
        // Motion Magic SmartDashboard Stuff
        SmartDashboard.putNumber("LeftError", Robot.drive.leftDriveError());
        SmartDashboard.putNumber("RightError", Robot.drive.rightDriveError());
        SmartDashboard.putNumber("RightDesired", Robot.drive.desiredRight);
        SmartDashboard.putNumber("LeftDesired", Robot.drive.desiredLeft);
        // Pressure and solenoid SmartDashboard stuff
        // SmartDashboard.putNumber("Raw Pressure", pressure.getValue());
        SmartDashboard.putNumber("Raw Pressure", Robot.pressure.getAverageValue());
        SmartDashboard.putNumber("PSI", (Robot.pressure.getAverageValue() - 400) * (70.0 / 1250.0));
        SmartDashboard.putNumber("Turret Postion", (Robot.turretEncoder.getAverageValue()));

        SmartDashboard.putNumber("Angle", Robot.drive.getCurrentAngle());
        // PID
        SmartDashboard.putNumber("KP", 0.005);
        SmartDashboard.putNumber("KI", 0);
        SmartDashboard.putNumber("KD", 0.05);
        SmartDashboard.putNumber("KF", .164);
        // Limelight stuff
        SmartDashboard.putNumber("x degrees off", Robot.limelight.targetXAngleFromCenter());
        SmartDashboard.putBoolean("Aimed", Math.abs(Robot.limelight.targetXAngleFromCenter()) < 1.5);
        SmartDashboard.putBoolean("seeing target?", Robot.limelight.isTargetSpotted());
        SmartDashboard.putNumber("y degrees off", Robot.limelight.targetYAngleFromCenter());
        SmartDashboard.putNumber("DistanceFromFiring", Robot.limelight.distanceIN());
        SmartDashboard.putNumber("Tangent", Robot.limelight.tangent());

    }

    public void GetPrefs() {
        prefs = Preferences.getInstance();
        /*
        leftMotorSpeed = prefs.getDouble("Left Motor Speed", .2);
        rightMotorSpeed = prefs.getDouble("Right Motor Speed", .5);
        angle = prefs.getDouble("Angle", 30);
    
        SmartDashboard.putNumber("Left Motor Speed", leftMotorSpeed);
        SmartDashboard.putNumber("Right Motor Speed", rightMotorSpeed);
        SmartDashboard.putNumber("Angle", angle);
        */
      }
}
