/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.automatic.actions.extras.SetShield;
import frc.automatic.runners.ActionQueue;
import frc.robot.random.*;
import frc.robot.robotmethods.*;
import frc.subsystems.*;


public class Robot extends TimedRobot {
  public static final Drive drive = new Drive(Constants.motorBL, Constants.motorBR, Constants.motorFL, Constants.motorFR);
  public static final Shooter shooter = new Shooter(Constants.shooterID);
  public static final Collector collector = new Collector(Constants.COLLECTOR_CONVERYER, Constants.COLLECTOR_WHEELS,
      Constants.PCMID, Constants.COLLECTOR_WHEEL_PUSH_FORWARD, Constants.COLLECTOR_WHEEL_PUSH_REVERSE);
  public static final Climber climb = new Climber(Constants.CLIMBER);
  public static final Turret turret = new Turret(Constants.turret);
  public static final Limelight limelight = new Limelight();
  public static final Compressor compressor = new Compressor(Constants.COMPRESSOR);
  public static Timer timer;
  public static final Elevator elevator = new Elevator(Constants.ElevatorID);
  public static AnalogInput pressure = new AnalogInput(0);
  public PigeonIMU pigeon = new PigeonIMU(Constants.Pidgeon);
  public static final edu.wpi.first.wpilibj.XboxController xboxcontroller = new XboxController(Constants.xboxcontroller);
  public static final Joystick rightJoystick = new Joystick(Constants.jstickR);
  public static final Joystick leftJoystick = new Joystick(Constants.jstickL);
  public static final Shield shield = new Shield(Constants.ShieldID);
  public static final SmartDashBoard smartDashBoard = new SmartDashBoard();
  public static final ActionLists actionList = new ActionLists();
  public static final TestMethods testMethods = new TestMethods();
  public static final TeleopMethods teleopMethods = new TeleopMethods();

  private Timer limelightTimer;
  private final ActionQueue autoActions = new ActionQueue();
  private final ActionQueue teleopActions = new ActionQueue();
  boolean runOnce = false;



  @Override
  public void robotInit() { //runs when the robot first starts up
    pigeon.setFusedHeading(0.0, 30);
    SmartDashboard.putString("RobotState", "Robot Disabled");

    compressor.setClosedLoopControl(true);
    //limelight.setDriverCamMode(true);
    //limelightTimer = new Timer(5);
  }

  @Override
  public void robotPeriodic() { //runs constantly no matter what state the robot is in

    //waits 5 secs then turns off the limelight
    //if (limelightTimer.isTimedOut() && !runOnce) {
      //limelight.setDriverCamMode(true);
     // runOnce = true;
    //}  

    //updates the angle of the robot
    //drive.DoPigeon();
    //displays all of our smartdashboad values
    //smartDashBoard.update();
  }



  @Override
  public void autonomousInit() {
    SmartDashboard.putString("RobotState", "Autonomous");

    collector.pushoutfrontwheel();
    drive.zeroCounters();
    drive.zeroIMU();
    turret.zeroTurret();

    autoActions.addAction(new SetShield());
    actionList.newAtonomose(autoActions);
  }

  @Override
  public void autonomousPeriodic() {
    autoActions.step();
  }



  @Override
  public void teleopInit() {
    teleopActions.clear();
    SmartDashboard.putString("RobotState", "TeleOp");
    teleopMethods.initialize(teleopActions);
  }
  
  @Override
  public void teleopPeriodic() {

    teleopMethods.driveTrain();

  }



  @Override
  public void disabledInit() {
    SmartDashboard.putString("RobotState", "Robot Disabled");
    limelight.setDriverCamMode(true);
  }



  @Override
  public void testInit() {
    SmartDashboard.putString("RobotState", "Test");
    testMethods.resetTimer();
  }

  @Override
  public void testPeriodic() {
    testMethods.runEachMotor();
    /*
    int teststate = 0;

    if (rightJoystick.getRawButtonPressed(1)) {
      teststate += 1;
      if (teststate > 4) teststate = 0;
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

    testMethods.drivetrainKF(3);*/
  }

}