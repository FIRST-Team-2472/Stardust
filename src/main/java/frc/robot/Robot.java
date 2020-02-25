/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
  public static final Collector collector = new Collector(Constants.COLLECTOR_CONVERYER, Constants.COLLECTOR_WHEELS);
  public static final Climber climb = null;
  public static final Turret turret = new Turret(Constants.turret);
  public static final Limelight limelight = new Limelight();
  public static final Indexer indexer = new Indexer(Constants.IndexerF, Constants.IndexerR);
  private final Happytwig joysticks = new Happytwig(Constants.jstickR);
  private final Happytwig joysticks2 = new Happytwig(Constants.jstickL);
  private final Vroomvroom xboxcontroller = new Vroomvroom(Constants.xboxcontroller);
  public static Timer timer;
  // there has to be a better way to say the imu is disabled
  // public static final ADIS16470_IMU imu = new ADIS16470_IMU();
  public static final ADIS16470_IMU imu = null;
  public static final frc.subsystems.Turret Turret = new Turret(Constants.turret);

  @Override
  public void robotInit() {
    // TODO SMART Dashboard INIT here
    SmartDashboard.putString("RobotState", "Robot On");
  }
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
    SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());
  }
  private final ActionQueue actionQueue = new ActionQueue();

  private void driveOverLineAuto(ActionQueue actions) {
    actions.clear();
    actions.addAction(new DriveStraightTime(.5 , 1.5));
  }

  private void shootBallAuto(ActionQueue actions) {
    actions.clear();
    actionQueue.addAction(new Aim());
    actionQueue.addAction(new Conveyor(1, .75));
    actionQueue.addAction(new StartShooter(1));
    actionQueue.addAction(new FeedBall());
    actionQueue.addAction(new FeedBall());
    actionQueue.addAction(new FeedBall());
    actionQueue.addAction(new StopShooter());
    
  }

  @Override
  public void autonomousInit() {
    driveOverLineAuto(actionQueue);
  }

  @Override
  public void autonomousPeriodic() {
    actionQueue.step();
  }

  @Override
  public void teleopInit() {
    // TODO initalize smart dashboard values / set teleop state
    SmartDashboard.putString("RobotState", "TeleopEnabled");
  }
  

  private final ActionQueue teleopActions = new ActionQueue();
  private boolean teleopShooting = false;
  @Override
  public void teleopPeriodic() {

    if (limelight.isTargetSpotted() && !teleopShooting) {
      teleopShooting = true;
      shootBallAuto(teleopActions);
    }
    if (teleopShooting) {
      xboxcontroller.setRumble(RumbleType.kLeftRumble, 1);
      if (teleopActions.step()) {
        //get that babe done
        teleopShooting = false;
      }
    }
    if (xboxcontroller.getRawButton(8)) {
      teleopShooting = false;
      teleopActions.abort();
    }

    drive.tankDrive(-joysticks2.getY(), -joysticks.getY());

    // Real coooolector
    collector.runConveyor(.75*-xboxcontroller.getY(Hand.kLeft));
    collector.runFrontWheels(.75*-xboxcontroller.getY(Hand.kRight));

    // FIXME what is the indexer?
    /* not plugged in
    if (xboxcontroller.getAButton()) {
      indexer.runIndexerForward();
    } else if (xboxcontroller.getXButton()) {
      indexer.runIndexerBackward();
    } else {
      indexer.runIndexerOff();
    }
    */
    // shooter control
    if (xboxcontroller.getYButton()) {
      shooter.runFlyWheel(1); // this should  be variable based on distance to the target
    } else {
      shooter.runFlyWheel(0);
    }

    // using the HAT switch?
    if (xboxcontroller.getBumper(GenericHID.Hand.kLeft)) {
      turret.runTurret(.25);
    } else if (xboxcontroller.getBumper(GenericHID.Hand.kRight)) {
      turret.runTurret(-.25);
    } else {
      turret.runTurret(0);
    }

    // NOTE: should probably have another control to prevent misfires since this can only be done once per match
    if (joysticks2.getRawButton(3) && joysticks.getRawButton(3)) {
      climb.runClimber(1);
    } else {
      climb.runClimber(0);
    }
  }

  @Override
  public void testInit() {
    // SMART Dashboard perfs
    final Preferences prefs = Preferences.getInstance();
    // FIXME give this a better name
    final double p = prefs.getDouble("PID p value", 0);
    SmartDashboard.putNumber("PID p value", p);
    final double i = prefs.getDouble("PID i VALUE", 0);
    // FIXME give this a better name
    SmartDashboard.putNumber("PID i value", i);
    final double f = prefs.getDouble("f", 0);
    SmartDashboard.putNumber("f", f);
    final int velocity = prefs.getInt("velocity", 0);
    SmartDashboard.putNumber("velocity", velocity);
    // FIXME give this a better name
    final double d = prefs.getDouble("d", 0);
    SmartDashboard.putNumber("d", d);
    final int acceleration = prefs.getInt("acceleration", 0);
    SmartDashboard.putNumber("acceleration", acceleration);

    Robot.drive.setupMotionMagic(f, p, i, d, velocity, acceleration);

    //TODO initalize the PID Test state
    
  }

  int teststate = 0;

  @Override
  public void testPeriodic() {

    if (joysticks.getRawButtonPressed(1)) {
      teststate += 1;
      if (teststate == 3) {
        teststate = 0;
      }
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
        drive.runBackRight(.25);
        SmartDashboard.putString("MotorsTest", "runBackRight");
      } else {
        drive.runBackRight(0);
      }
      if (xboxcontroller.getYButton()) {
        drive.runFrontLeft(.25);
        SmartDashboard.putString("MotorsTest", "runFrontLeft");
      } else {
        drive.runFrontLeft(0);
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
        collector.runConveyor(.25);
        SmartDashboard.putString("MotorsTest", "runCollector");
      }else {
        collector.runConveyor(0);
      }
      /* not plugged in
      if (xboxcontroller.getBButton()) {
        indexer.runIndexerForward();
        SmartDashboard.putString("MotorsTest", "runIndexerForward");
      }else if(xboxcontroller.getYButton()) {
        indexer.runIndexerBackward();
        SmartDashboard.putString("MotorsTest", "runIndexerBackward");
      }else {
        indexer.runIndexerOff();
      }
      */
      if (xboxcontroller.getXButton()) {
        turret.runTurret(.25);
        SmartDashboard.putString("MotorsTest", "runTurret");
      }else {
        turret.runTurret(0);
      }
      break;
      case 2:
      if (xboxcontroller.getAButton()) {
        shooter.runFlyWheel(.25);
        SmartDashboard.putString("MotorsTest", "runShooter");
      } else {
        shooter.runFlyWheel(0);
      }
      if (xboxcontroller.getYButton()) {
        climb.runClimber(.25);
        SmartDashboard.putString("MotorsTest", "runClimberRight");
      } else {
        climb.runClimber(0);
      }
    case 3:

    default:
    }

  }

}
