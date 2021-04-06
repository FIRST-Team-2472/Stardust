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
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
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
  Preferences prefs;
  double leftMotorSpeed, rightMotorSpeed, angle, change, change2;
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
    GetPrefs();
  }

  // Assorted SmartDashboard things. Both revolve around the Limelight's abilities
  // to see the target and track it. Untested in serious play.
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("x degrees off", limelight.targetXAngleFromCenter());
    SmartDashboard.putBoolean("seeing target?", limelight.isTargetSpotted());
    drive.DoPigeon();
    SmartDashboard.putNumber("Angle", drive.getCurrentAngle());
    limelight.distanceIN();
    updateSmartDashboard();
  }

  private final ActionQueue actionQueue = new ActionQueue();

  // Drives the robot for a set period of time. Hopefully will be rendered useless
  // due to Motion Magic
  /*
   * private void driveOverLineAuto(ActionQueue actions) { actions.clear();
   * actions.addAction(new DriveStraightTime(-0.5, 1.5)); }
   */
  // Autonomous code. Only to be used with the Limelight.
  // In theory, it points and shoots the shooter AFTER the target is aimed.
  // Untested as of 2/13/21.
  private void trackDrive(ActionQueue actions) {
    actions.clear();
    actionQueue.addAction(new ZeroIMU());
    actionQueue.addAction(new DriveStraightIMU(1, 0));
    actionQueue.addAction(new DriveTowardHeading(0, .4, 60));
    actionQueue.addAction(new DriveStraightIMU(1, 60));
    actionQueue.addAction(new DriveTowardHeading(.4, 0, 5));
    actionQueue.addAction(new TurnRobot(0));
    actionQueue.addAction(new DriveStraightVelocity(8.5));
    actionQueue.addAction(new DriveTowardHeading(.3, 0, -60));
    actionQueue.addAction(new DriveStraightVelocity(2));
    actionQueue.addAction(new DriveTowardHeading(0.05, .45, 220));
    actionQueue.addAction(new DriveStraightVelocity(4));
    actionQueue.addAction(new DriveTowardHeading(.3, 0, 260));
    actionQueue.addAction(new DriveStraightVelocity(8.5));
    actionQueue.addAction(new DriveTowardHeading(0.3, 0, 120));
    actionQueue.addAction(new DriveStraightVelocity(2));
    actionQueue.addAction(new DriveTowardHeading(0, .3, 180));
  }

  private void zigZag(ActionQueue actions) {
    actions.clear();
    actions.addAction(new DriveTowardHeading(.6, .4, -20));
    actions.addAction(new DriveTowardHeading(.4, .6, 20));
    actions.addAction(new DriveTowardHeading(.6, .4, -20));
    actions.addAction(new DriveTowardHeading(.4, .6, 20));
    actions.addAction(new DriveTowardHeading(.6, .4, -20));
    actions.addAction(new DriveTowardHeading(.4, .6, 20));
  }

  private void loadBallsAuto(ActionQueue actions) {
    actions.addAction(new DriveStraightTime(.5, 5));
    actions.addAction(new DumpBalls(3));
  }

  private void seekTheFleshMeat(ActionQueue actions) {
    actions.addAction(new TurnRobot(180));
    actions.addAction(new Wait(5));
    actions.addAction(new Seek());
    actions.addAction(new Wait(5));
    actions.addAction(new Aim());
    actions.addAction(new Wait(5));
  }

  private void FIRE(ActionQueue actions) {
    actions.clear();
    actions.addAction(new Aim());
    actions.addAction(new RangeFinding());
    actions.addAction(new ShieldAim());
    actions.addAction(new Shooting(5));
  }

  private void runBounceCourse(ActionQueue actions) {
    actions.addAction(new DriveStraightVelocity(1.5));
    actions.addAction(new TurnRobot(90));
    actions.addAction(new ZeroCounters());
    actions.addAction(new DriveStraightVelocity(2.5));
    actions.addAction(new TurnRobot(-150));
    actions.addAction(new ZeroCounters());
    actions.addAction(new DriveStraightVelocity(10));
    actions.addAction(new TurnRobot(60));
    actions.addAction(new ZeroCounters());
    actions.addAction(new DriveStraightVelocity(2.5));
    actions.addAction(new TurnRobot(85));
    actions.addAction(new ZeroCounters());
    actions.addAction(new DriveStraightVelocity(8));
    actions.addAction(new TurnRobot(180));
    actions.addAction(new ZeroCounters());
    actions.addAction(new DriveStraightVelocity(9));
    actions.addAction(new TurnRobot(80));
    actions.addAction(new ZeroCounters());
    actions.addAction(new DriveStraightVelocity(6));
    actions.addAction(new TurnRobot(85));
    actions.addAction(new ZeroCounters());
    actions.addAction(new DriveStraightVelocity(8));
    actions.addAction(new TurnRobot(-160));
    actions.addAction(new ZeroCounters());
    actions.addAction(new DriveStraightVelocity(4));
    actions.addAction(new TurnRobot(60));
    actions.addAction(new ZeroCounters());
    actions.addAction(new DriveStraightVelocity(2.5));
  }

  @Override
  public void autonomousInit() {
    drive.zeroCounters();
    drive.zeroIMU();
    actionQueue.clear();
    updateSmartDashboard();
    GetPrefs();
    //runBounceCourse(actionQueue);
    trackDrive(actionQueue);

    /*actionQueue.addAction(new TurnToHeading(0.1, 0.6, 90));
    actionQueue.addAction(new Wait(0.2));
    actionQueue.addAction(new TurnRobot(90));*/
    //actionQueue.addAction(new DriveTowardHeading(0.12, 0.5, 80));
    /*actionQueue.addAction(new TurnRobot(180));
    actionQueue.addAction(new Wait(5));
    actionQueue.addAction(new Seek());
    actionQueue.addAction(new Wait(5));
    actionQueue.addAction(new Aim());
    actionQueue.addAction(new Wait(5));*/


    
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


    //actionQueue.addAction(new DriveTowardHeading(leftMotorSpeed, rightMotorSpeed, angle));
    //actionQueue.addAction(new RangeFinding());
    //actionQueue.addAction(new DriveTowardHeading(leftMotorSpeed, rightMotorSpeed, angle));

  }

  @Override
  public void autonomousPeriodic() {
    GetPrefs();
    updateSmartDashboard();


    actionQueue.step();
  }

  @Override
  public void teleopInit() {
    turret.zeroTurret();
    updateSmartDashboard();
    //compressor.setClosedLoopControl(true);
    drive.zeroCounters();
    SmartDashboard.putString("RobotState", "TeleopEnabled");
   
  }

  private final ActionQueue teleopActions = new ActionQueue();
  
  //private boolean teleopShooting = false;

  @Override
  public void teleopPeriodic() {
    GetPrefs();
    updateSmartDashboard();
    // runs the IMU(Pigeon), and related things

    /*if (limelight.isTargetSpotted() && teleopShooting) {
      teleopShooting = false;
    }
    if (!teleopShooting && xboxcontroller.getAButtonPressed() && limelight.isTargetSpotted()) {
      teleopShooting = true;
    }
    if (xboxcontroller.getBButtonPressed()) {
      teleopShooting = false;
      teleopActions.abort();
    }*/

    /*if (xboxcontroller.getXButton()) {
      shooter.runFlyWheel(-1);
    } else {
      shooter.runFlyWheel(0);
    }*/

    //shooter.runFlyWheel(-1);

    //One or the other
    drive.tankDriveVelocity(leftJoystick.getY() * -.5, rightJoystick.getY() * -.5);
    //drive.arcadeDriveVelocity(leftJoystick.getY()*-.5, rightJoystick.getX()*-.5);

    collector.runConveyor(.7 * -xboxcontroller.getRawAxis(1));
    collector.runFrontWheels(.5 * -xboxcontroller.getRawAxis(2));

    if (xboxcontroller.getBumper(GenericHID.Hand.kRight)) {
      collector.pushoutfrontwheel();
    } else if (xboxcontroller.getBumper(GenericHID.Hand.kLeft)) {
      collector.pushinfrontwheel();
    } else {
      collector.pushofffrontwheel();
    }

    if (limelight.isTargetSpotted() && teleopShooting) {
      teleopShooting = false;
    }
    if (!teleopShooting && xboxcontroller.getAButtonPressed() && limelight.isTargetSpotted()) {
      teleopShooting = true;
      FIRE(teleopActions);
    }
    if (xboxcontroller.getBButtonPressed()) {
      teleopShooting = false;
      teleopActions.abort();
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
    GetPrefs();
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

  // run this to set all smart dashboard values
  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Left Joystick value", leftJoystick.getY());
    SmartDashboard.putNumber("Left Speed:", drive.getLeftSpeed());
    SmartDashboard.putNumber("Right Speed:", drive.getRightSpeed());
    // SMART Dashboard perfs
    GetPrefs();
    // Distance SmartDashboard stuff
    SmartDashboard.putNumber("RightDistance", drive.getRightDistance());
    SmartDashboard.putNumber("LeftDistance", drive.getLeftDistance());
    SmartDashboard.putNumber("TurretDistance", turret.getTurretDistance());
    SmartDashboard.putNumber("Right Distance inches", drive.getRightDistance()/(Constants.pulsesPerFoot/12));
    SmartDashboard.putNumber("Left Distance inches", drive.getLeftDistance()/(Constants.pulsesPerFoot/12));
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
    SmartDashboard.putBoolean("Aimed", Math.abs(limelight.targetXAngleFromCenter())<1.5);
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