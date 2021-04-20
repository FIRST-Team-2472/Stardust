/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.ActionQueue;
import frc.actions.*;
import frc.subsystems.Climber;
import frc.subsystems.Drive;
import frc.subsystems.Elevator;
import frc.subsystems.Shooter;
import frc.subsystems.Turret;
import frc.subsystems.Collector;
import frc.subsystems.Shield;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static final Drive drive = new Drive(Constants.motorBL, Constants.motorBR, Constants.motorFL, Constants.motorFR);
  public static final Shooter shooter = new Shooter(Constants.shooterID);
  public static final Collector collector = new Collector(Constants.COLLECTOR_CONVERYER, Constants.COLLECTOR_WHEELS,
      Constants.PCMID, Constants.COLLECTOR_WHEEL_PUSH_FORWARD, Constants.COLLECTOR_WHEEL_PUSH_REVERSE);
  public static final Climber climb = new Climber(Constants.CLIMBER);
  public static final Turret turret = new Turret(Constants.turret);
  public static final Limelight limelight = new Limelight();
  private static final Compressor compressor = new Compressor(Constants.COMPRESSOR);
  public static Timer timer;
  public static final Elevator elevator = new Elevator(Constants.ElevatorID);
  public AnalogInput pressure = new AnalogInput(0);
  public AnalogInput turretEncoder = new AnalogInput(1);
  public PigeonIMU pigeon = new PigeonIMU(Constants.Pidgeon);
  public static final frc.subsystems.Turret Turret = new Turret(Constants.turret);
  public static final edu.wpi.first.wpilibj.XboxController xboxcontroller = new XboxController(Constants.xboxcontroller);
  public static final Joystick rightJoystick = new Joystick(Constants.jstickR);
  public static final Joystick leftJoystick = new Joystick(Constants.jstickL);
  public static final Shield shield = new Shield(Constants.ShieldID);
  public static final SmartDashBoard smartDashBoard = new SmartDashBoard();
  public static final ActionLists actionList = new ActionLists();

  Preferences prefs;
  double leftMotorSpeed, rightMotorSpeed, angle, change, change2;
  int driveState;
  boolean teleopShooting;

  @Override
  public void robotInit() {
    pigeon.setFusedHeading(0.0, 30);
    SmartDashboard.putString("RobotState", "Robot On");
    compressor.setClosedLoopControl(true);
    /*
     * while (limelight.getPipeline() != 3) { limelight.setPipeline(3); }
     */
    limelight.setLedMode(Limelight.LED_FORCE_OFF);
    limelight.setDriverCamMode(true);
    smartDashBoard.GetPrefs();
  }

  // Assorted SmartDashboard things. Both revolve around the Limelight's abilities
  // to see the target and track it. Untested in serious play.
  @Override
  public void robotPeriodic() {
    drive.DoPigeon();
    limelight.distanceIN();
    smartDashBoard.update();
  }

  private final ActionQueue autoActions = new ActionQueue();


  
  @Override
  public void autonomousInit() {
    drive.zeroCounters();
    drive.zeroIMU();
    autoActions.clear();
    smartDashBoard.GetPrefs();
    //Used to remove start problem DO NOT touch, Wes
    autoActions.addAction(new Wait(0));
<<<<<<< HEAD
    autoActions.addAction(new DriveStraightIMU(20,0));
    autoActions.addAction(new DriveStraightIMU(-20,0));
    autoActions.addAction(new DriveStraightIMU(20,0));
    autoActions.addAction(new DriveStraightIMU(-20,0));
    autoActions.addAction(new DriveStraightIMU(20,0));
    autoActions.addAction(new DriveStraightIMU(-20,0));
=======

>>>>>>> parent of f2d1881 (fixes and something)

    //for turning right
    
    /*change = Math.random() * (50-20+1) +20;
    change2 = (int)change;
    leftMotorSpeed = change2/100;
    change = Math.random() * ((leftMotorSpeed-.1)*100 +1);
    change2 = (int)change;
    rightMotorSpeed = change2/100;
    angle = (int)(Math.random() * (180-10+1)+10);*/
    

    //for turning left
    /*
    change = Math.random() * (50-20+1) +20;
    change2 = (int)change;
    rightMotorSpeed = change2/100;
    change = Math.random() * ((rightMotorSpeed-.1)*100+1);
    change2 = (int)change;
    leftMotorSpeed = change2/100;
    angle = (int)(Math.random() * (180-10+1)+10);
    */

    SmartDashboard.putNumber("Auto Left Motor Speed", leftMotorSpeed);
    SmartDashboard.putNumber("Auto Right Motor Speed", rightMotorSpeed);
    SmartDashboard.putNumber("Auto Heading", angle);
  }

  @Override
  public void autonomousPeriodic() {
    smartDashBoard.GetPrefs();

    autoActions.step();
  }

  @Override
  public void teleopInit() {
    turret.zeroTurret();
    drive.zeroCounters();
    SmartDashboard.putString("RobotState", "TeleopEnabled");
    driveState = 0;
  }

  private final ActionQueue teleopActions = new ActionQueue();
  
  @Override
  public void teleopPeriodic() {
    smartDashBoard.GetPrefs();

    
    if (leftJoystick.getRawButtonPressed(1)) {
      driveState++;
      if (driveState > 1) {
        driveState = 0;
      }
    }

    if (driveState == 0) {
      drive.tankDriveVelocity(leftJoystick.getY() * -.5, rightJoystick.getY() * -.5);
      SmartDashboard.putString("Drive State", "Tonk ;)");
    }
    else if (driveState == 1) {
      drive.arcadeDriveVelocity(leftJoystick.getY()*-.5, leftJoystick.getX()*-.5);
      SmartDashboard.putString("Drive State", "Arcade");
    }

    if (xboxcontroller.getTriggerAxis(GenericHID.Hand.kRight) > .6) turret.runTurret(-.25);
    if (xboxcontroller.getTriggerAxis(GenericHID.Hand.kLeft) > .6) turret.runTurret(.25);

    collector.runConveyor(.7 * -xboxcontroller.getRawAxis(1));
    collector.runFrontWheels(.5 * -xboxcontroller.getRawAxis(2));

    if (xboxcontroller.getBumper(GenericHID.Hand.kRight)) collector.pushoutfrontwheel();
    else if (xboxcontroller.getBumper(GenericHID.Hand.kLeft)) collector.pushinfrontwheel();
    else collector.pushofffrontwheel();

    if (limelight.isTargetSpotted() && teleopShooting) teleopShooting = false;
    if (!teleopShooting && xboxcontroller.getAButtonPressed() && limelight.isTargetSpotted()) {
      teleopShooting = true;
      actionList.FIRE(teleopActions);
    }
    if (xboxcontroller.getBButtonPressed()) {
      teleopShooting = false;
      teleopActions.abort();
    }

<<<<<<< HEAD
    if (xboxcontroller.getXButtonPressed()) elevator.runElevatorPower(.5);
    else elevator.runElevatorPower(0);
=======
    if (leftJoystick.getRawButton(1)) {
      
    }
>>>>>>> parent of f2d1881 (fixes and something)

    // NOTE: should probably have another control to prevent misfires since this can
    // only be done once per match
    if (leftJoystick.getRawButton(3) && rightJoystick.getRawButton(3)) {
      climb.runClimber(1);
    } else {
      climb.runClimber(0);
    }

    if (leftJoystick.getRawButton(2) && rightJoystick.getRawButton(2)) {
      climb.runClimber(-1);
    } else {
      climb.runClimber(0);
    }

    teleopActions.step();
  }

  @Override
  public void testInit() {
    // SMART Dashboard perfs
    smartDashBoard.GetPrefs();
  }

  int teststate = 0;
  public double suggestKF = 0;

  @Override
  public void testPeriodic() {
    if (rightJoystick.getRawButtonPressed(1)) {
      while (rightJoystick.getRawButtonPressed(1)) {
      }
      teststate += 1;
      if (teststate > 4) {
        teststate = 0;
      }
      System.out.println(teststate);
    }
    SmartDashboard.putNumber("teststate", teststate);
    switch (teststate) {
      // Test case that has the xboxcontroller independently control each motor.
      case 0:
        collector.runConveyor(-xboxcontroller.getY(Hand.kLeft));
        collector.runFrontWheels(-xboxcontroller.getY(Hand.kRight));
        if (xboxcontroller.getAButton()) {
          drive.runBackLeft(.25);
          SmartDashboard.putString("MotorsTest", "runBackLeft");
        } else {
          drive.runBackLeft(0);
        }
        if (xboxcontroller.getBButton()) {
          drive.runBackRightPower(.25);
          SmartDashboard.putString("MotorsTest", "runBackRight");
        } else {
          drive.runBackRightPower(0);
        }
        if (xboxcontroller.getYButton()) {
          drive.runFrontLeftPower(.25);
          SmartDashboard.putString("MotorsTest", "runFrontLeft");
        } else {
          drive.runFrontLeftPower(0);
        }
        if (xboxcontroller.getXButton()) {
          drive.runFrontRight(.25);
          SmartDashboard.putString("MotorsTest", "runFrontRight");
        } else {
          drive.runFrontRight(0);
        }
        break;
      // Test case that run each different area of motors separately for testing.
      case 1:
        if (xboxcontroller.getAButton()) {
          collector.runConveyor(.65);
          SmartDashboard.putString("MotorsTest", "runCollector");
        } else {
          collector.runConveyor(0);

        }
        if (xboxcontroller.getBButton()) {
          collector.runFrontWheels(-.25);
          SmartDashboard.putString("MotorsTest", "runotherth9ingageaefawef");
        } else {
          collector.runFrontWheels(0);
        }
        /*if (xboxcontroller.getXButton()) {
          turret.runTurret(.25);
          SmartDashboard.putString("MotorsTest", "runTurret");
        } else {
          turret.runTurret(0);
        }*/
        if (xboxcontroller.getYButton()) {
          shooter.runFlyWheel(.25);
          SmartDashboard.putString("MotorsTest", "runShooter");
        } else {
          shooter.runFlyWheel(0);
        }
        break;
      // Climber test case. Made during one of the only competitions in 2020, the
      // Grand Forks Great Northern competition.
      case 2:
        if (xboxcontroller.getAButton()) {
          climb.runClimber(.25);
          SmartDashboard.putString("MotorsTest", "runClimber");
        }
        if (xboxcontroller.getXButton()) {
          climb.runClimber(-.25);
          SmartDashboard.putString("MotorsTest", "runClimber");
        }
        if (!xboxcontroller.getXButton() && !xboxcontroller.getAButton()) {
          climb.runClimber(0);
        }
        break;
      // Test case that runs all the pistons. Pistons that were for show, mostly.
      case 3:
        if (xboxcontroller.getYButton()) {
          collector.pushoutfrontwheel();
          SmartDashboard.putString("PistonTest", "runFrontPistonForward");
        }
        if (xboxcontroller.getXButton()) {
          collector.pushinfrontwheel();
          SmartDashboard.putString("PistonTest", "runFrontPistonBackward");
        }
        if (!xboxcontroller.getXButton() && !xboxcontroller.getYButton()) {
          collector.pushofffrontwheel();
        }
        break;
      // Test case for tank drive.
      case 4:

        drive.tankDriveVelocity(1, 1);
        int leftSpeed = drive.getLeftSpeed();
        int rightSpeed = drive.getRightSpeed();
        SmartDashboard.putNumber("Left Speed:", leftSpeed);
        SmartDashboard.putNumber("Right Speed:", rightSpeed);

        break;

      default:
    }
    if (rightJoystick.getRawButton(2)) {
      shooter.runFlyWheel(.75);
      suggestKF = .75 * 1023 / shooter.runSensorVelocity();
      System.out.print("Suggested kF: ");
      System.out.println(suggestKF);
    } else {
      shooter.runFlyWheel(0);
    }

    if (rightJoystick.getRawButton(3)) {
      drive.runBackRightPower(.75);
      drive.runFrontLeftPower(.75);
      suggestKF = .75 * 1023 / drive.getRightSpeed();
      System.out.print("Suggested Right kF: ");
      System.out.println(suggestKF);
      suggestKF = .75 * 1023 / drive.getLeftSpeed();
      System.out.print("Suggested Left kF: ");
      System.out.println(suggestKF);
    } else {
      drive.runBackRightPower(0);
    }
  }
}