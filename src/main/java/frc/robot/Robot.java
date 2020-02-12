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

  public static final Drive drive = new Drive(Constants.motorBL, Constants.motorBR, Constants.motorFL,
      Constants.motorFR);
  public static final Shooter shooter = new Shooter(Constants.shooterID);
  public static final Collector collector = new Collector(Constants.converyer);
  public static final Climber climb = null;
  public static final Turret turret = new Turret(Constants.turret);
  public static final Limelight limelight = new Limelight();
  public static final Indexer indexer = new Indexer(Constants.IndexerF, Constants.IndexerR);
  private final Happytwig joysticks = new Happytwig(Constants.jstickR);
  private final Happytwig joysticks2 = new Happytwig(Constants.jstickL);
  private final Vroomvroom xboxcontroller = new Vroomvroom(Constants.xboxcontroller);
  public static Timer timer = null;
  // public static final ADIS16470_IMU imu = new ADIS16470_IMU();
  public static final ADIS16470_IMU imu = null;
  public static final frc.subsystems.Turret Turret = new Turret(Constants.turret);
   
  @Override
  public void robotInit() {
   
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
    SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());
  }

  /*public void disabledInit() {
    SmartDashboard.getInstance();
    SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
    SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());
  }

  @Override
  public void disabledInit() {
    SmartDashboard.putString("actionName", "Disabled");
  }*/

  private final ActionQueue actionQueue = new ActionQueue();

  @Override
  public void autonomousInit() {

    actionQueue.clear();

    actionQueue.addAction(new DriveStraight(0.5, 2));
    actionQueue.addAction(new Turn(180));
    //actionQueue.addAction(new Aim());
    /*
     * // Shooting actionQueue.addAction(new Aim()); //actionQueue.addAction(new
     * StartShooter()); //actionQueue.addAction(new FeedBall());
     */
  }

  @Override
  public void autonomousPeriodic() {
    actionQueue.step();
  }

  @Override
  public void teleopInit() {

  }

  @Override
  public void teleopPeriodic() {
    drive.tankDrive(joysticks.getY(), joysticks2.getY());

    if (joysticks.getRawButton(1)) {
      collector.runConveyor(1);
    } else if (joysticks2.getRawButton(1)) {
      collector.runConveyor(-1);
    } else {
      collector.runConveyor(0);
    }
    if (xboxcontroller.getAButton()) {
      indexer.runIndexerForward();
    } else if (xboxcontroller.getXButton()) {
      indexer.runIndexerBackward();
    } else {
      indexer.runIndexerOff();
    }
    if (xboxcontroller.getBumper(GenericHID.Hand.kLeft)) {
      turret.runTurret(.25);
    } else if (xboxcontroller.getBumper(GenericHID.Hand.kRight)) {
      turret.runTurret(-.25);
    } else {
      turret.runTurret(0);
    }
    if (xboxcontroller.getYButton()) {
      shooter.runFlyWheel(.25);
    } else {
      shooter.runFlyWheel(0);
    }
    if (xboxcontroller.getBButton()) {
      climb.runClimber(1);
    } else {
      climb.runClimber(0);
    }
  }

  @Override
  public void testInit() {
    final Preferences prefs = Preferences.getInstance();
    final double p = prefs.getDouble("p", 0);
    SmartDashboard.putNumber("p", p);
    final double i = prefs.getDouble("i", 0);
    SmartDashboard.putNumber("i", i);
    final double f = prefs.getDouble("f", 0);
    SmartDashboard.putNumber("f", f);
    final int velocity = prefs.getInt("velocity", 0);
    SmartDashboard.putNumber("velocity", velocity);
    final double d = prefs.getDouble("d", 0);
    SmartDashboard.putNumber("d", d);
    final int acceleration = prefs.getInt("acceleration", 0);
    SmartDashboard.putNumber("acceleration", acceleration);

    Robot.drive.setupMotionMagic(f, p, i, d, velocity, acceleration);
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
    switch (teststate) {
    case 0:
      if (xboxcontroller.getAButton()) {
        drive.runBackLeft(.25);
      } else {
        drive.runBackLeft(0);
      }
      if (xboxcontroller.getBButton()) {
        drive.runBackRight(.25);
      } else {
        drive.runBackRight(0);
      }
      if (xboxcontroller.getYButton()) {
        drive.runFrontLeft(.25);
      } else {
        drive.runFrontLeft(0);
      }
      if (xboxcontroller.getXButton()) {
        drive.runFrontRight(.25);
      } else {
        drive.runFrontRight(0);
      }
      break;
      case 1:
      if (xboxcontroller.getAButton()) {
        collector.runConveyor(.25);
      }else {
        collector.runConveyor(0);
      }
      if (xboxcontroller.getBButton()) {
        indexer.runIndexerForward();
      }else if(xboxcontroller.getYButton()) {
        indexer.runIndexerBackward();
      }else {
        indexer.runIndexerOff();
      }
      if (xboxcontroller.getXButton()) {
        turret.runTurret(.25);
      }else {
        turret.runTurret(0);
      }
      break;
      case 2:
      if (xboxcontroller.getAButton()) {
        shooter.runFlyWheel(.25);
      } else {
        shooter.runFlyWheel(0);
      }
      if (xboxcontroller.getBButton()) {
        climb.runClimberL(.25);
      } else {
        climb.runClimberL(0);
      }
      if (xboxcontroller.getYButton()) {
        climb.runClimberR(.25);
      } else {
        climb.runClimberR(0);
      }
    case 3:

    default:
    }

  }

}
