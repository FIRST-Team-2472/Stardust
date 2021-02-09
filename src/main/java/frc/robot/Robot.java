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
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.ActionQueue;
import frc.actions.*;
import frc.subsystems.Climber;
import frc.subsystems.Drive;
import frc.subsystems.Indexer;
import frc.subsystems.Shooter;
import frc.subsystems.Turret;
import frc.subsystems.Collector;
import com.analog.adis16470.frc.ADIS16470_IMU;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
//do we have popcorn?
  public static final Drive drive = new Drive(Constants.motorBL, Constants.motorBR, Constants.motorFL,Constants.motorFR);
  public static final Shooter shooter = new Shooter(Constants.shooterID);
  public static final Collector collector = new Collector(Constants.COLLECTOR_CONVERYER, Constants.COLLECTOR_WHEELS, Constants.PCMID, Constants.COLLECTOR_WHEEL_PUSH_FORWARD, Constants.COLLECTOR_WHEEL_PUSH_REVERSE);
  public static final Climber climb = new Climber(Constants.CLIMBER);
  public static final Turret turret = new Turret(Constants.turret);
  public static final Limelight limelight = new Limelight();
  public static final Indexer indexer = new Indexer(Constants.IndexerF, Constants.IndexerR);
  private final Happytwig rightJoystick = new Happytwig(Constants.jstickR);
  private final Happytwig leftJoystick = new Happytwig(Constants.jstickL);
  private final Vroomvroom xboxcontroller = new Vroomvroom(Constants.xboxcontroller);
  private static final Compressor compressor = new Compressor(Constants.COMPRESSOR);
  public static Timer timer;
  // there has to be a better way to say the imu is disabled
  // public static final ADIS16470_IMU imu = new ADIS16470_IMU();
  // weeeeeeeeeeeeeeeee
  public static final ADIS16470_IMU imu = null;
  public static final frc.subsystems.Turret Turret = new Turret(Constants.turret);

  @Override
  public void robotInit() {
    SmartDashboard.putString("RobotState", "Robot On");
    compressor.setClosedLoopControl(true);
    /*
    while (limelight.getPipeline() != 3) {
      limelight.setPipeline(3);
    }
    */
    limelight.setLedMode(Limelight.LED_FORCE_OFF);
    limelight.setDriverCamMode(true);

  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
    SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());
  }

  private final ActionQueue actionQueue = new ActionQueue();

  private void driveOverLineAuto(ActionQueue actions) {
    actions.clear();
    actions.addAction(new DriveStraightTime(-0.5, 1.5));
  }

  private void shootBallAuto(ActionQueue actions) {
    actions.clear();
    actions.addAction(new Aim());
    actions.addAction(new Conveyor(1, .75));
    actions.addAction(new StartShooter(1));
    actions.addAction(new FeedBall());
    actions.addAction(new FeedBall());
    actions.addAction(new FeedBall());
    actions.addAction(new StopShooter());
  }

  private void loadBallsAuto(ActionQueue actions) {
    actions.addAction(new DriveStraightTime(.5, 5));
    actions.addAction(new DumpBalls(3));

  }

  @Override
  public void autonomousInit() {
    updateSmartDashboard();
    drive.zeroCounters();
    //Full speed = 6250 pulse per 1/10th of a second
      //int leftSpeed = drive.getLeftSpeed();
      //int rightSpeed = drive.getRightSpeed();
      //SmartDashboard.putNumber("Left Speed:", leftSpeed);
      //SmartDashboard.putNumber("Right Speed:", rightSpeed);}
    //driveOverLineAuto(actionQueue);
    //loadBallsAuto(actionQueue);
    //actionQueue.addAction(new DriveDistance(555));
    //actionQueue.addAction(new PushFrontWheels());
    //shootBallAuto(actionQueue);
    //limelight.setPipeline(Limelight.PIPELINE_DRIVER_CAM);
    //limelight.setLedMode(Limelight.LED_DEFAULT_TO_PIPELINE);
    //limelight.setDriverCamMode(false);
   }
  
  Preferences preferences = Preferences.getInstance();

  @Override
  public void autonomousPeriodic() {
    updateSmartDashboard();
  drive.tankDriveVelocity(1, 1);
    return;
    //drive.driverFeet(99);
    /*if (rightJoystick.getRawButtonPressed(1)) {
      final int p = preferences.getInt("kP", 2);
      final int i = preferences.getInt("kI", 0);
      final int d = preferences.getInt("kD", 0);
      final int f = preferences.getInt("kF", 1);
      drive.setupMotionMagic(f, p, i, d, 8000, 1000);
      SmartDashboard.putNumber("kP", p);
      SmartDashboard.putNumber("kI", i);
      SmartDashboard.putNumber("kD", d);
      SmartDashboard.putNumber("kF", f);
      SmartDashboard.putNumber("RightDistance", drive.getRightDistance());
      SmartDashboard.putNumber("LeftDistance", drive.getLeftDistance());
    }*/

    //actionQueue.step();
    //SmartDashboard.putNumber("MotionMagicError", drive.driveError());
  }

  @Override
  public void teleopInit() {
    updateSmartDashboard();
    compressor.setClosedLoopControl(true);
    drive.zeroCounters();
    // TODO initalize smart dashboard values / set teleop state
    SmartDashboard.putString("RobotState", "TeleopEnabled");
  }

  private final ActionQueue teleopActions = new ActionQueue();
  private boolean teleopShooting = false;

  @Override
  public void teleopPeriodic() {

    SmartDashboard.putNumber("RightDistance", drive.getRightDistance());
    SmartDashboard.putNumber("LeftDistance", drive.getLeftDistance());

    SmartDashboard.putNumber("LeftError", drive.leftDriveError());
    SmartDashboard.putNumber("RightError", drive.rightDriveError());

    SmartDashboard.putNumber("Left Speed:", drive.getLeftSpeed());
    SmartDashboard.putNumber("Right Speed:", drive.getRightSpeed());

    SmartDashboard.putNumber("RightDesired", drive.desiredRight);
    SmartDashboard.putNumber("LeftDesired", drive.desiredLeft);

    if (limelight.isTargetSpotted() && teleopShooting) {
      teleopShooting = false;
      shootBallAuto(teleopActions);
    }
    if (!teleopShooting && xboxcontroller.getAButtonPressed() && limelight.isTargetSpotted()) {
      teleopShooting = true;
    }
    if (xboxcontroller.getBButtonPressed()) {
      teleopShooting = false;
      teleopActions.abort();
    }

    if (xboxcontroller.getXButton()) {
      shooter.runFlyWheel(-1);
    } else {
      shooter.runFlyWheel(0);
    }

    drive.tankDriveVelocity(leftJoystick.getY(), rightJoystick.getY());

    // Real coooolector
    collector.runConveyor(.7 * -xboxcontroller.getRawAxis(1));
    collector.runFrontWheels(.5 * -xboxcontroller.getRawAxis(2));

    if (xboxcontroller.getYButton()) {
      indexer.runIndexerForward();
    } else {
      indexer.runIndexerBackward();
    }

    // using the HAT switch?
    if (xboxcontroller.getBumper(GenericHID.Hand.kRight)) {
      collector.pushoutfrontwheel();
    } else if (xboxcontroller.getBumper(GenericHID.Hand.kLeft)) {
      collector.pushinfrontwheel();
    } else {
      collector.pushofffrontwheel();
    }

    if (xboxcontroller.getTriggerAxis(GenericHID.Hand.kRight) > .6) {
      turret.runTurret(.25);
    } else if (xboxcontroller.getTriggerAxis(GenericHID.Hand.kLeft) > .6) {
      turret.runTurret(-.25);
    } else {
      turret.runTurret(0);
    }

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
    final Preferences prefs = Preferences.getInstance();
    // FIXME give this a better name
    final double p = prefs.getDouble("PID p value", 0);
    SmartDashboard.putNumber("PID p value", p);
    final double i = prefs.getDouble("PID i value", 0);
    // FIXME give this a better name
    SmartDashboard.putNumber("PID i value", i);
    final double f = prefs.getDouble("PID f value", 0);
    SmartDashboard.putNumber("PID f value", f);
    final int velocity = prefs.getInt("velocity", 0);
    SmartDashboard.putNumber("velocity", velocity);
    // FIXME give this a better name
    final double d = prefs.getDouble("PID d value", 0);
    SmartDashboard.putNumber("PID d value", d);
    final int acceleration = prefs.getInt("acceleration", 0);
    SmartDashboard.putNumber("acceleration", acceleration);

    Robot.drive.setupMotionMagic(f, p, i, d, velocity, acceleration);

    // TODO initalize the PID Test state
 }
  

  int teststate = 0;
  public double suggestKF = 0;


  @Override
  public void testPeriodic() {
    //Robot.drive.setupMotionMagic(f, p, i, d, velocity, acceleration);

    if (rightJoystick.getRawButtonPressed(1)) {
      while(rightJoystick.getRawButtonPressed(1) ) {
      }
      teststate += 1;
      if (teststate > 4) {
        teststate = 0;
      }
      System.out.println(teststate);
    }
    SmartDashboard.putNumber("teststate", teststate);
    switch (teststate) {
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
      if (xboxcontroller.getXButton()) {
        turret.runTurret(.25);
        SmartDashboard.putString("MotorsTest", "runTurret");
      } else {
        turret.runTurret(0);
      }
      if (xboxcontroller.getYButton()) {
        shooter.runFlyWheel(.25);
        SmartDashboard.putString("MotorsTest", "runShooter");
      } else {
        shooter.runFlyWheel(0);
      }
      break;
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
    case 3:
      if (xboxcontroller.getAButton()) {
        indexer.runIndexerForward();
        SmartDashboard.putString("PistonTest", "runFeedPistonForward");
      }
      if (xboxcontroller.getBButton()) {
        indexer.runIndexerBackward();
        SmartDashboard.putString("PistonTest", "runFeedPistonBackward");
      }
      if (!xboxcontroller.getBButton() && !xboxcontroller.getAButton()) {
        indexer.runIndexerOff();
      }
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
  }else {
      shooter.runFlyWheel(0);
    }
  }
  public void updateSmartDashboard(){
    SmartDashboard.putNumber("Left Speed:", drive.getLeftSpeed());
    SmartDashboard.putNumber("Right Speed:", drive.getRightSpeed());
        // SMART Dashboard perfs
        final Preferences prefs = Preferences.getInstance();
        // FIXME give this a better name
        final double p = prefs.getDouble("PID p value", 0);
        SmartDashboard.putNumber("PID p value", p);
        final double i = prefs.getDouble("PID i value", 0);
        // FIXME give this a better name
        SmartDashboard.putNumber("PID i value", i);
        final double f = prefs.getDouble("PID f value", 0);
        SmartDashboard.putNumber("PID f value", f);
        final int velocity = prefs.getInt("velocity", 0);
        SmartDashboard.putNumber("velocity", velocity);
        // FIXME give this a better name
        final double d = prefs.getDouble("PID d value", 0);
        SmartDashboard.putNumber("PID d value", d);
        final int acceleration = prefs.getInt("acceleration", 0);
        SmartDashboard.putNumber("acceleration", acceleration);
        SmartDashboard.putNumber("RightDistance", drive.getRightDistance());
        SmartDashboard.putNumber("LeftDistance", drive.getLeftDistance());
        SmartDashboard.putString("RobotState", "TeleopEnabled");
        SmartDashboard.putNumber("kP", p);
      SmartDashboard.putNumber("kI", i);
      SmartDashboard.putNumber("kD", d);
      SmartDashboard.putNumber("kF", f);
      SmartDashboard.putNumber("RightDistance", drive.getRightDistance());
      SmartDashboard.putNumber("LeftDistance", drive.getLeftDistance());
      SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
      SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());
      SmartDashboard.putString("RobotState", "Robot On");
      SmartDashboard.putNumber("LeftError", drive.leftDriveError());
      SmartDashboard.putNumber("RightError", drive.rightDriveError());
      SmartDashboard.putNumber("RightDesired", drive.desiredRight);
      SmartDashboard.putNumber("LeftDesired", drive.desiredLeft);
  }
}
