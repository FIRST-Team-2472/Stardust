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
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.ActionQueue;
import frc.actions.*;
import frc.subsystems.Climber;
import frc.subsystems.Drive;
import frc.subsystems.Indexer;
import frc.subsystems.Shooter;
import frc.subsystems.Turret;
import frc.subsystems.Collector;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static final Drive drive = new Drive(Constants.motorBL, Constants.motorBR, Constants.motorFL,Constants.motorFR);
  public static final Shooter shooter = new Shooter(Constants.shooterID);
  public static final Collector collector = new Collector(Constants.COLLECTOR_CONVERYER, Constants.COLLECTOR_WHEELS, Constants.PCMID, Constants.COLLECTOR_WHEEL_PUSH_FORWARD, Constants.COLLECTOR_WHEEL_PUSH_REVERSE);
  public static final Climber climb = new Climber(Constants.CLIMBER);
  public static final Turret turret = new Turret(Constants.turret);
  public static final Limelight limelight = new Limelight();
  public static final Indexer indexer = new Indexer(Constants.IndexerF, Constants.IndexerR);
  private static final Compressor compressor = new Compressor(Constants.COMPRESSOR);
  public static Timer timer;
  public AnalogInput pressure = new AnalogInput(0);
  public PigeonIMU pigeon = new PigeonIMU(Constants.Pidgeon);
  public static final frc.subsystems.Turret Turret = new Turret(Constants.turret);
  public static final edu.wpi.first.wpilibj.XboxController xboxcontroller = new XboxController(Constants.xboxcontroller);
  public static final Joystick rightJoystick = new Joystick(Constants.jstickR);
  public static final Joystick leftJoystick = new Joystick(Constants.jstickL);

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

//Assorted SmartDashboard things. Both revolve around the Limelight's abilities to see the target and track it. Untested in serious play.
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
    SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());
  }

  private final ActionQueue actionQueue = new ActionQueue();
//Drives the robot for a set period of time. Hopefully will be rendered useless due to Motion Magic
  private void driveOverLineAuto(ActionQueue actions) {
    actions.clear();
    actions.addAction(new DriveStraightTime(-0.5, 1.5));
  }
  //Autonomous code. Only to be used with the Limelight. 
  //In theory, it points and shoots the shooter AFTER the target is aimed. Untested as of 2/13/21.
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
//More untested autonomous code! Not even useful here, as our current robot can't hold more than 1 ball in the shooter. Oh well.
  private void loadBallsAuto(ActionQueue actions) {
    actions.addAction(new DriveStraightTime(.5, 5));
    actions.addAction(new DumpBalls(3));

  }


  @Override
  public void autonomousInit() {
    pigeon.setFusedHeading(0.0, 30);
    drive.zeroCounters();
    actionQueue.clear();
    updateSmartDashboard();
    actionQueue.addAction(new DriveDistance(5));
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
  
    actionQueue.step();
    }

  @Override
  public void teleopInit() {
    pigeon.setFusedHeading(0.0, 30);
    updateSmartDashboard();
    compressor.setClosedLoopControl(true);
    drive.zeroCounters();
    SmartDashboard.putString("RobotState", "TeleopEnabled");
  }

  private final ActionQueue teleopActions = new ActionQueue();
  private boolean teleopShooting = false;

  @Override
  public void teleopPeriodic() {
    updateSmartDashboard();
    SmartDashboard.putNumber("RightDistance", drive.getRightDistance());
    SmartDashboard.putNumber("LeftDistance", drive.getLeftDistance());

    SmartDashboard.putNumber("LeftError", drive.leftDriveError());
    SmartDashboard.putNumber("RightError", drive.rightDriveError());

    SmartDashboard.putNumber("Left Speed:", drive.getLeftSpeed());
    SmartDashboard.putNumber("Right Speed:", drive.getRightSpeed());

    SmartDashboard.putNumber("RightDesired", drive.desiredRight);
    SmartDashboard.putNumber("LeftDesired", drive.desiredLeft);

    //runs the IMU(Pigeon), and related things
    drive.DoPigeon();

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
 }
  

  int teststate = 0;
  public double suggestKF = 0;


  @Override
  public void testPeriodic() {

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
    //Test case that has the xboxcontroller independently control each motor.
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
    //Test case that run each different area of motors separately for testing.   
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
    //Climber test case. Made during one of the only competitions in 2020, the Grand Forks Great Northern competition.
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
    //Test case that runs all the pistons. Pistons that were for show, mostly.
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
    //Test case for tank drive.
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

  //run this to set all smart dashboard values
  public void updateSmartDashboard(){
    SmartDashboard.putNumber("Left Speed:", drive.getLeftSpeed());
    SmartDashboard.putNumber("Right Speed:", drive.getRightSpeed());
        // SMART Dashboard perfs
        final Preferences prefs = Preferences.getInstance();
      //Distance SmartDashboard stuff
      SmartDashboard.putNumber("RightDistance", drive.getRightDistance());
      SmartDashboard.putNumber("LeftDistance", drive.getLeftDistance());
      //Robot state SmartDashboard stuff
      SmartDashboard.putString("RobotState", "TeleopEnabled");
      SmartDashboard.putString("RobotState", "Robot On");
      //Motion Magic SmartDashboard Stuff
      SmartDashboard.putNumber("LeftError", drive.leftDriveError());
      SmartDashboard.putNumber("RightError", drive.rightDriveError());
      SmartDashboard.putNumber("RightDesired", drive.desiredRight);
      SmartDashboard.putNumber("LeftDesired", drive.desiredLeft);
      //Pressure and solenoid SmartDashboard stuff
      //SmartDashboard.putNumber("Raw Pressure", pressure.getValue());
      SmartDashboard.putNumber("Raw Pressure", pressure.getAverageValue());
      SmartDashboard.putNumber("PSI", (pressure.getAverageValue()-400)*(70.0/1250.0));
      //PID
      SmartDashboard.putNumber("KP", 0.005);
      SmartDashboard.putNumber("KI", 0);
      SmartDashboard.putNumber("KD", 0.05);
      SmartDashboard.putNumber("KF", .164);
      //Limelight stuff
      SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
      SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());

  }
}
