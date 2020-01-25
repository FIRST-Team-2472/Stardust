/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.subsystems.Drive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  private Drive drive;
  private Happytwig joysticks;
  private Happytwig joysticks2;
  private Vroomvroom xboxcontroller;
  //private DoubleSolo

  @Override
  public void robotInit() {
    drive = new Drive(Constants.motorBL, Constants.motorBR, Constants.motorFL, Constants.motorFR);
    joysticks = new Happytwig(Constants.jstickR);
    joysticks2 = new Happytwig(Constants.jstickL);
    xboxcontroller = new Vroomvroom(Constants.xboxcontroller);
    
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  
  }

  @Override
  public void teleopPeriodic() {
    drive.tankDrive(joysticks.getY(), joysticks2.getY());
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
