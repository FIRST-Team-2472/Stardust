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

  public static final Drive drive = new Drive(Constants.motorBL, Constants.motorBR, Constants.motorFL, Constants.motorFR);
  public static final Shooter shooter = new Shooter(Constants.shooterID);
  public static final Collector collector = new Collector(Constants.conveyor);
  public static final Climber climb = new Climber(Constants.ClimberL, Constants.ClimberR, Constants.PullClimberL, Constants.PullClimberR);
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
   
  }

  @Override
  public void robotPeriodic() {
    // Tasks that the robot should always be doing (Lights?)
    SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
    SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());
  }

  /*public void disabledInit() {
    SmartDashboard.getInstance();
    SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
    SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());

    // TODO set all motors to 0 and disbale the compressor
  }*/ 

  @Override
  public void disabledInit() {
    SmartDashboard.putString("actionName", "Disabled");
  }

  private final ActionQueue actionQueue = new ActionQueue();

  @Override
  public void autonomousInit() {
    // FIXME different atonomous modes should be built out into separate functions
    actionQueue.clear();

    actionQueue.addAction(new DriveStraight(0.5, 2));
    // why are we turning?
    //actionQueue.addAction(new Turn(180));
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
    // TODO initalize smart dashboard values / set teleop state
  }

  @Override
  public void teleopPeriodic() {
    drive.tankDrive(joysticks.getY(), joysticks2.getY());

    // NOTE joystick trigger (probably should be on the xbox controler)
    if (joysticks.getRawButton(1)) {
      collector.runConveyor(1);
    } else if (joysticks2.getRawButton(1)) {
      collector.runConveyor(-1);
    } else {
      collector.runConveyor(0);
    }

    // FIXME what is the indexer?
    if (xboxcontroller.getAButton()) {
      indexer.runIndexerForward();
    } else if (xboxcontroller.getXButton()) {
      indexer.runIndexerBackward();
    } else {
      indexer.runIndexerOff();
    }

    // shooter control
    if (xboxcontroller.getYButton()) {
      shooter.runFlyWheel(); // this should  be variable based on distance to the target
    } else {
      shooter.runFlyWheel();
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
    if (xboxcontroller.getBButton()) {
      climb.runClimber(1);
    } else {
      climb.runClimber(0);
    }
  }

  @Override
  public void testInit() {
    // SMART Dashboard perfs
    Preferences prefs = Preferences.getInstance();
    // FIXME give this a better name
    double p = prefs.getDouble("p", 0);
    SmartDashboard.putNumber("p", p);
    double i = prefs.getDouble("i", 0);
    // FIXME give this a better name
    SmartDashboard.putNumber("i", i);
    final double f = prefs.getDouble("f", 0);
    SmartDashboard.putNumber("f", f);
    final int velocity = prefs.getInt("velocity", 0);
    SmartDashboard.putNumber("velocity", velocity);
    // FIXME give this a better name
    double d = prefs.getDouble("d", 0);
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
    switch (teststate) {
    case 0:
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
      if (xboxcontroller.getBButton()) {
        indexer.runIndexerForward();
        SmartDashboard.putString("MotorsTest", "runIndexerForward");
      }else if(xboxcontroller.getYButton()) {
        indexer.runIndexerBackward();
        SmartDashboard.putString("MotorsTest", "runIndexerBackward");
      }else {
        indexer.runIndexerOff();
      }
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
      if (xboxcontroller.getBButton()) {
        climb.runClimberL(.25);
        SmartDashboard.putString("MotorsTest", "runClimberLeft");
        climb.runClimberL(0);
      }
      if (xboxcontroller.getYButton()) {
        climb.runClimberR(.25);
        SmartDashboard.putString("MotorsTest", "runClimberRight");
      } else {
        climb.runClimberR(0);
      }
    case 3:

    default:
    }

  }

}
