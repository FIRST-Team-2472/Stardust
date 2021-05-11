package frc.robot.robotmethods;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.ActionQueue;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID;

public class TeleopMethods {
int driveState, cooldown;
boolean teleopShooting, changePistons;

    public void initialize() {
        Robot.turret.zeroTurret();
        Robot.drive.zeroCounters();
        Robot.shield.zeroCounters();
        SmartDashboard.putString("RobotState", "TeleopEnabled");
        driveState = 0;
        cooldown = 0;
    }

    public void driveTrain() {
        //switches drive state from tank to acrade drive
        if (Robot.leftJoystick.getRawButtonPressed(1)) {
            driveState++;
            if (driveState > 1) {
                driveState = 0;
            }
        }
  
        if (driveState == 0) {
            //runs tank drive
            Robot.drive.tankDriveVelocity(Robot.leftJoystick.getY() * -.5, Robot.rightJoystick.getY() * -.5);
            SmartDashboard.putString("Drive State", "Tonk ;)");
        }
        else if (driveState == 1) {
            //runs arcade drive
            Robot.drive.arcadeDriveVelocity(Robot.leftJoystick.getY()*-.5, Robot.leftJoystick.getX()*-.5);
            SmartDashboard.putString("Drive State", "Arcade");
        }
    }

    public void runTurret() {
         //runs the turret
        if (Robot.xboxcontroller.getTriggerAxis(GenericHID.Hand.kRight) > .6) Robot.turret.runTurret(-.25);
        else if (Robot.xboxcontroller.getTriggerAxis(GenericHID.Hand.kLeft) > .6) Robot.turret.runTurret(.25);
        else Robot.turret.runTurret(0);
    }

    public void runCollector() {
        if (Math.abs(Robot.xboxcontroller.getRawAxis(1)) > Math.abs(Robot.xboxcontroller.getRawAxis(5))) {
            //runs the intake for the lower elvator and collector wheels
            Robot.collector.runConveyorPower(.7 * -Math.abs(Robot.xboxcontroller.getRawAxis(1)));
            Robot.collector.runFrontWheels(.5 * -Math.abs(Robot.xboxcontroller.getRawAxis(1)));
        } else {
            //runs the outtake for the lower elvator and collector wheels
            Robot.collector.runConveyorPower(.7 * Math.abs(Robot.xboxcontroller.getRawAxis(5)));
            Robot.collector.runFrontWheels(.5 * Math.abs(Robot.xboxcontroller.getRawAxis(5)));
        }

        
    }

    public void shooting(ActionQueue teleopActions) {
        if (Robot.limelight.isTargetSpotted() && teleopShooting) teleopShooting = false;
        if (!teleopShooting && Robot.xboxcontroller.getAButtonPressed() && Robot.limelight.isTargetSpotted()) {
            teleopShooting = true;
            Robot.actionList.FIRE(teleopActions);
        }
        if (Robot.xboxcontroller.getBButtonPressed()) {
            teleopShooting = false;
            teleopActions.abort();
        }
    }

    public void manualFire(){
         //runs top elvator
        if (Robot.xboxcontroller.getXButton()) {
            Robot.shooter.runFlyWheelPower(-1);
            Robot.elevator.runElevatorPower(0.5);
        }
        else {
            Robot.shooter.runFlyWheelPower(0);
            Robot.elevator.runElevatorPower(0);
        }

        if (Robot.xboxcontroller.getPOV() == 0 || Robot.xboxcontroller.getPOV() == 45 || Robot.xboxcontroller.getPOV() == 315) {
            Robot.shield.runShieldPower(.2);
        }
        else if (Robot.xboxcontroller.getPOV() == 180 || Robot.xboxcontroller.getPOV() == 135 || Robot.xboxcontroller.getPOV() == 270) {
            Robot.shield.runShieldPower(-.2);
        }
        else {
            Robot.shield.runShieldPower(0);
        }
    }
    
    public void moveFrontWheels(){
        //pushes out pistons to collect balls
        if (cooldown > 200) {
            if (Robot.xboxcontroller.getBumper(GenericHID.Hand.kRight) && changePistons == false) {
                Robot.collector.pushoutfrontwheel();
                changePistons = true;
                cooldown = 0;
            }
            else if (Robot.xboxcontroller.getBumper(GenericHID.Hand.kRight) && changePistons == true) {
                Robot.collector.pushinfrontwheel();
                changePistons = false;
                cooldown = 0;
            }
            else Robot.collector.pushofffrontwheel();
        } else cooldown++;
    }

    public void runClimber(){
        //would run climber if we had one
        if (Robot.leftJoystick.getRawButtonPressed(3) && Robot.rightJoystick.getRawButton(3)) {
            Robot.climb.runClimber(1);
          } else {
            Robot.climb.runClimber(0);
          }
          
          if (Robot.leftJoystick.getRawButton(2) && Robot.rightJoystick.getRawButton(2)) {
            Robot.climb.runClimber(-1);
          } else {
            Robot.climb.runClimber(0);
        }
    }
}