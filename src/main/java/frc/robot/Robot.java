/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static final Drive drive = new Drive(Constants.motorBL, Constants.motorBR, Constants.motorFL, Constants.motorFR);
  public static Shooter shooter;
  private Collector slurp;
  private Climber climb;
  public static Turret turret = new Turret(Constants.turret);
  public static Limelight limelight = new Limelight();
  public static Indexer indexer;
  private final Happytwig joysticks = new Happytwig(Constants.jstickR);
  private final Happytwig joysticks2 = new Happytwig(Constants.jstickL);
  private final Vroomvroom xboxcontroller = new Vroomvroom(Constants.xboxcontroller);
  //public static final ADIS16470_IMU imu = new ADIS16470_IMU();
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
  
  @Override
  public void disabledInit() {
    SmartDashboard.putString("actionName", "Disabled");
  }

  private ActionQueue actionQueue = new ActionQueue();
  
  @Override
  public void autonomousInit() {

    
    actionQueue.clear();
    

    actionQueue.addAction(new DriveStraight(.5, 2));
    actionQueue.addAction(new Wait(5));
    actionQueue.addAction(new DriveStraight(-.5, 2));

    /*// Shooting
    actionQueue.addAction(new Aim());
    //actionQueue.addAction(new StartShooter());
    //actionQueue.addAction(new FeedBall());
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
    drive.tankDrive(joysticks.getY() * 1, joysticks2.getY() * 1);




  }

    Preferences prefs = Preferences.getInstance();

  @Override
  public void testInit() {
    double p = prefs.getDouble("p", 0);
    SmartDashboard.putNumber("p", p);
    double i = prefs.getDouble("i", 0);
    SmartDashboard.putNumber("i", i);
    double f = prefs.getDouble("f", 0);
    SmartDashboard.putNumber("f", f);
    int velocity = prefs.getInt("velocity", 0);
    SmartDashboard.putNumber("velocity", velocity);
    double d = prefs.getDouble("d", 0);
    SmartDashboard.putNumber("d", d);
    int acceleration = prefs.getInt("acceleration", 0);
    SmartDashboard.putNumber("acceleration", acceleration);

    Robot.drive.setupMotionMagic(f, p, i, d, velocity, acceleration);
  }

  @Override
  public void testPeriodic() {
    drive.driverMeters(2);
    /*turret.runTurret(joysticks.getY());
    if (xboxcontroller.getAButton()) {
      // do somting
      drive.runBackLeft(.25);
    }
    if (xboxcontroller.getBButton()) {
      // do somting
      drive.runBackRight(.25);
    }
    if (xboxcontroller.getYButton()) {
      // do somting
      drive.runFrontLeft(.25);
    }
    if (xboxcontroller.getXButton()) {
      // do somting
      drive.runFrontRight(.25);
    }
    
    if (xboxcontroller.getTriggerAxis(Vroomvroom.Hand.kLeft) > .5) {
      shooter.runFlyWheel(1);
    }

    if (xboxcontroller.getTriggerAxis(Vroomvroom.Hand.kRight) > .5) {
      slurp.runConveyor(1);
    }
    */
  
  }

}
 