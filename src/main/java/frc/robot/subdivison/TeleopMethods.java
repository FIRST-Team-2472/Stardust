package frc.robot.subdivison;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import frc.actions.runners.ActionQueue;

public class TeleopMethods {
int driveState;
boolean teleopShooting;
private final ActionQueue teleopActions = new ActionQueue();
    public void initialize() {
        Robot.turret.zeroTurret();
        Robot.drive.zeroCounters();
        SmartDashboard.putString("RobotState", "TeleopEnabled");
        driveState = 0;
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

    public void runTurret(){
         //runs the turret
        if (Robot.xboxcontroller.getTriggerAxis(GenericHID.Hand.kRight) > .6) Robot.turret.runTurret(-.25);
        else if (Robot.xboxcontroller.getTriggerAxis(GenericHID.Hand.kLeft) > .6) Robot.turret.runTurret(.25);
        else Robot.turret.runTurret(0);
    }

    public void runCollector(){
        if (Math.abs(Robot.xboxcontroller.getRawAxis(1)) > Math.abs(Robot.xboxcontroller.getRawAxis(5))) {
            //runs the intake for the lower elvator and collector wheels
            Robot.collector.runConveyor(.7 * -Math.abs(Robot.xboxcontroller.getRawAxis(1)));
            Robot.collector.runFrontWheels(.5 * -Math.abs(Robot.xboxcontroller.getRawAxis(1)));
        } else {
            //runs the outtake for the lower elvator and collector wheels
            Robot.collector.runConveyor(.7 * Math.abs(Robot.xboxcontroller.getRawAxis(5)));
            Robot.collector.runFrontWheels(.5 * Math.abs(Robot.xboxcontroller.getRawAxis(5)));
        }

        //pushes out pistons to collect balls
        if (Robot.xboxcontroller.getBumper(GenericHID.Hand.kRight)) Robot.collector.pushoutfrontwheel();
        else if (Robot.xboxcontroller.getBumper(GenericHID.Hand.kLeft)) Robot.collector.pushinfrontwheel();
        else Robot.collector.pushofffrontwheel();
    }

    public void shooting(){
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
}
