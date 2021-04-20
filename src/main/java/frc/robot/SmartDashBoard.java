package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashBoard extends Robot {
    public void update() {
        SmartDashboard.putNumber("Left Joystick value", leftJoystick.getY());
        SmartDashboard.putNumber("Left Speed:", drive.getLeftSpeed());
        SmartDashboard.putNumber("Right Speed:", drive.getRightSpeed());
        // SMART Dashboard perfs
        GetPrefs();
        // Distance SmartDashboard stuff
        SmartDashboard.putNumber("RightDistance", drive.getRightDistance());
        SmartDashboard.putNumber("LeftDistance", drive.getLeftDistance());
        SmartDashboard.putNumber("TurretDistance", turret.getTurretDistance());
        SmartDashboard.putNumber("Right Distance inches", drive.getRightDistance() / (Constants.pulsesPerFoot / 12));
        SmartDashboard.putNumber("Left Distance inches", drive.getLeftDistance() / (Constants.pulsesPerFoot / 12));
        SmartDashboard.getNumber("Flywheel", shooter.runSensorVelocity());
        // Robot state SmartDashboard stuff
        SmartDashboard.putString("RobotState", "TeleopEnabled");
        SmartDashboard.putString("RobotState", "Robot On");
        // Motion Magic SmartDashboard Stuff
        SmartDashboard.putNumber("LeftError", drive.leftDriveError());
        SmartDashboard.putNumber("RightError", drive.rightDriveError());
        SmartDashboard.putNumber("RightDesired", drive.desiredRight);
        SmartDashboard.putNumber("LeftDesired", drive.desiredLeft);
        // Pressure and solenoid SmartDashboard stuff
        // SmartDashboard.putNumber("Raw Pressure", pressure.getValue());
        SmartDashboard.putNumber("Raw Pressure", pressure.getAverageValue());
        SmartDashboard.putNumber("PSI", (pressure.getAverageValue() - 400) * (70.0 / 1250.0));
        SmartDashboard.putNumber("Turret Postion", (turretEncoder.getAverageValue()));

        SmartDashboard.putNumber("Angle", drive.getCurrentAngle());
        // PID
        SmartDashboard.putNumber("KP", 0.005);
        SmartDashboard.putNumber("KI", 0);
        SmartDashboard.putNumber("KD", 0.05);
        SmartDashboard.putNumber("KF", .164);
        // Limelight stuff
        SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
        SmartDashboard.putBoolean("Aimed", Math.abs(limelight.targetXAngleFromCenter()) < 1.5);
        SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());
        SmartDashboard.putNumber("y degrees off", limelight.targetYAngleFromCenter());
        SmartDashboard.putNumber("DistanceFromFiring", limelight.distanceIN());
        SmartDashboard.putNumber("Tangent", limelight.tangent());

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
