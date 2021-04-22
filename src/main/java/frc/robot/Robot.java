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
import frc.robot.subdivison.*;

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
  public static AnalogInput pressure = new AnalogInput(0);
  public static AnalogInput turretEncoder = new AnalogInput(1);
  public PigeonIMU pigeon = new PigeonIMU(Constants.Pidgeon);
  public static final frc.subsystems.Turret Turret = new Turret(Constants.turret);
  public static final edu.wpi.first.wpilibj.XboxController xboxcontroller = new XboxController(Constants.xboxcontroller);
  public static final Joystick rightJoystick = new Joystick(Constants.jstickR);
  public static final Joystick leftJoystick = new Joystick(Constants.jstickL);
  public static final Shield shield = new Shield(Constants.ShieldID);
  public static final SmartDashBoard smartDashBoard = new SmartDashBoard();
  public static final ActionLists actionList = new ActionLists();
  public static final TestMethods testMethods = new TestMethods();

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
    autoActions.addAction(new DriveStraightIMU(20,0));
    autoActions.addAction(new DriveStraightIMU(-20,0));
    autoActions.addAction(new DriveStraightIMU(20,0));
    autoActions.addAction(new DriveStraightIMU(-20,0));
    autoActions.addAction(new DriveStraightIMU(20,0));
    autoActions.addAction(new DriveStraightIMU(-20,0));





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

    //switches drive state from tank to acrade drive
    if (leftJoystick.getRawButtonPressed(1)) {
      driveState++;
      if (driveState > 1) {
        driveState = 0;
      }
    }

    if (driveState == 0) {
      //runs tank drive
      drive.tankDriveVelocity(leftJoystick.getY() * -.5, rightJoystick.getY() * -.5);
      SmartDashboard.putString("Drive State", "Tonk ;)");
    }
    else if (driveState == 1) {
      //runs arcade drive
      drive.arcadeDriveVelocity(leftJoystick.getY()*-.5, leftJoystick.getX()*-.5);
      SmartDashboard.putString("Drive State", "Arcade");
    }

    //runs the turret
    if (xboxcontroller.getTriggerAxis(GenericHID.Hand.kRight) > 1) turret.runTurret(-.25);
    else if (xboxcontroller.getTriggerAxis(GenericHID.Hand.kLeft) > 1) turret.runTurret(.25);
    else turret.runTurret(0);

    //runs the lower elevator
    collector.runConveyor(.7 * -xboxcontroller.getRawAxis(1));
    //front collector wheels
    collector.runFrontWheels(.5 * -xboxcontroller.getRawAxis(2));

    //pushes out pistons to collect balls
    if (xboxcontroller.getBumper(GenericHID.Hand.kRight)) collector.pushoutfrontwheel();
    else if (xboxcontroller.getBumper(GenericHID.Hand.kLeft)) collector.pushinfrontwheel();
    else collector.pushofffrontwheel();

    //
    if (limelight.isTargetSpotted() && teleopShooting) teleopShooting = false;
    if (!teleopShooting && xboxcontroller.getAButtonPressed() && limelight.isTargetSpotted()) {
      teleopShooting = true;
      actionList.FIRE(teleopActions);
    }
    if (xboxcontroller.getBButtonPressed()) {
      teleopShooting = false;
      teleopActions.abort();
    }

    //runs top elvator
    if (xboxcontroller.getXButton()) elevator.runElevatorPower(0.5);
    else elevator.runElevatorPower(0);

    //would run climber if we had one
    if (leftJoystick.getRawButtonPressed(3) && rightJoystick.getRawButton(3)) {
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
      testMethods.case0();
       break;
      case 1:
       testMethods.case1();
       break;
      case 2:
       testMethods.case2();
       break;
      case 3:
       testMethods.case3();
       break;
      case 4:
       testMethods.case4();
       break;

      default:
    }
    testMethods.shooterKF(2);

    testMethods.drivetrainKF(3);
  }
}