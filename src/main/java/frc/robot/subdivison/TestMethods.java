package frc.robot.subdivison;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
    
public class TestMethods {
  public double suggestKF = 0;

    public void case0() {
      Robot.collector.runConveyor(-Robot.xboxcontroller.getY(Joystick.Hand.kLeft));
      Robot.collector.runFrontWheels(-Robot.xboxcontroller.getY(Joystick.Hand.kRight));
        if (Robot.xboxcontroller.getAButton()) {
          Robot.drive.runBackLeft(.25);
          SmartDashboard.putString("MotorsTest", "runBackLeft");
        } else {
          Robot.drive.runBackLeft(0);
        }
        if (Robot.xboxcontroller.getBButton()) {
          Robot.drive.runBackRightPower(.25);
          SmartDashboard.putString("MotorsTest", "runBackRight");
        } else {
          Robot.drive.runBackRightPower(0);
        }
        if (Robot.xboxcontroller.getYButton()) {
          Robot.drive.runFrontLeftPower(.25);
          SmartDashboard.putString("MotorsTest", "runFrontLeft");
        } else {
          Robot.drive.runFrontLeftPower(0);
        }
        if (Robot.xboxcontroller.getXButton()) {
          Robot.drive.runFrontRight(.25);
          SmartDashboard.putString("MotorsTest", "runFrontRight");
        } else {
          Robot.drive.runFrontRight(0);
        }
      // Test case that run each different area of motors separately for testing.
    }
    public void case1() {
        if (Robot.xboxcontroller.getAButton()) {
            Robot.collector.runConveyor(.65);
            SmartDashboard.putString("MotorsTest", "runCollector");
          } else {
            Robot.collector.runConveyor(0);
  
          }
          if (Robot.xboxcontroller.getBButton()) {
            Robot.collector.runFrontWheels(-.25);
            SmartDashboard.putString("MotorsTest", "runotherth9ingageaefawef");
          } else {
            Robot.collector.runFrontWheels(0);
          }
          /*if (xboxcontroller.getXButton()) {
            turret.runTurret(.25);
            SmartDashboard.putString("MotorsTest", "runTurret");
          } else {
            turret.runTurret(0);
          }*/
          if (Robot.xboxcontroller.getYButton()) {
            Robot.shooter.runFlyWheel(.25);
            SmartDashboard.putString("MotorsTest", "runShooter");
          } else {
            Robot.shooter.runFlyWheel(0);
          }
        // Climber test case. Made during one of the only competitions in 2020, the
        // Grand Forks Great Northern competition.
    }
    public void case2() {
        if (Robot.xboxcontroller.getAButton()) {
            Robot.climb.runClimber(.25);
            SmartDashboard.putString("MotorsTest", "runClimber");
          }
          if (Robot.xboxcontroller.getXButton()) {
            Robot.climb.runClimber(-.25);
            SmartDashboard.putString("MotorsTest", "runClimber");
          }
          if (!Robot.xboxcontroller.getXButton() && !Robot.xboxcontroller.getAButton()) {
            Robot.climb.runClimber(0);
          }
        // Test case that runs all the pistons. Pistons that were for show, mostly.
    }
    public void case3() {
      if (Robot.xboxcontroller.getYButton()) {
        Robot.collector.pushoutfrontwheel();
        SmartDashboard.putString("PistonTest", "runFrontPistonForward");
      }
      if (Robot.xboxcontroller.getXButton()) {
        Robot.collector.pushinfrontwheel();
        SmartDashboard.putString("PistonTest", "runFrontPistonBackward");
      }
      if (!Robot.xboxcontroller.getXButton() && !Robot.xboxcontroller.getYButton()) {
        Robot.collector.pushofffrontwheel();
      }
    // Test case for tank drive.
    }
    public void case4() {
      Robot.drive.tankDriveVelocity(1, 1);
        int leftSpeed = Robot.drive.getLeftSpeed();
        int rightSpeed = Robot.drive.getRightSpeed();
        SmartDashboard.putNumber("Left Speed:", leftSpeed);
        SmartDashboard.putNumber("Right Speed:", rightSpeed);
    }
    public void shooterKF(int joystickButton) {
      if (Robot.rightJoystick.getRawButton(joystickButton)) {
        Robot.shooter.runFlyWheel(.75);
        suggestKF = .75 * 1023 / Robot.shooter.runSensorVelocity();
        System.out.print("Suggested kF: ");
        System.out.println(suggestKF);
      } else {
        Robot.shooter.runFlyWheel(0);
      }
    }
    public void drivetrainKF(int joystickButton) {
      if (Robot.rightJoystick.getRawButton(joystickButton)) {
        Robot.drive.runBackRightPower(.75);
        Robot.drive.runFrontLeftPower(.75);
        suggestKF = .75 * 1023 / Robot.drive.getRightSpeed();
        System.out.print("Suggested Right kF: ");
        System.out.println(suggestKF);
        suggestKF = .75 * 1023 / Robot.drive.getLeftSpeed();
        System.out.print("Suggested Left kF: ");
        System.out.println(suggestKF);
      } else {
        Robot.drive.runBackRightPower(0);
      }
    }
}