package frc.robot.robotmethods;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.actions.drivestraight.*;
import frc.automatic.actions.extras.*;
import frc.automatic.actions.shooting.*;
import frc.automatic.actions.zerosensors.*;
import frc.automatic.runners.ActionQueue;

public class ActionLists {

      public void driveStraight(ActionQueue actions, double distance) {
        actions.addAction(new ZeroEncoder());
        actions.addAction(new Wait(0.01));
        actions.addAction(new DriveStraightVelocity(distance));
      }
    
      public void loadBallsAuto(ActionQueue actions) {
        actions.addAction(new DriveStraightTime(.5, 5));
        actions.addAction(new DumpBalls(3));
      }
    
      public void Aim(ActionQueue actions) {
        Robot.limelight.setDriverCamMode(false);
        actions.addAction(new Wait(0.1));
        actions.addAction(new AimHorizontally());
        actions.addAction(new AimVertically());
      }
    
      public void FIRE_telop(ActionQueue actions) {
        actions.addAction(new StartFlyWheel(2));
        actions.addAction(new Shoot());
      }

      public void FIRE_auto(ActionQueue actions) {
        actions.addAction(new StartFlyWheel(1.5));
        actions.addAction(new Shoot_Auto(3));
      }

      public void randomTurnRight (ActionQueue actions, int angle) {
        double leftMotorSpeed, rightMotorSpeed;
        
        leftMotorSpeed = ((int)(Math.random() * (75) + 20))/100;
        rightMotorSpeed = ((int)(Math.random() * ((leftMotorSpeed-.1)*100)))/100;
        actions.addAction(new DriveTowardHeading(leftMotorSpeed, rightMotorSpeed, angle));

        SmartDashboard.putNumber("Random Left Motor Speed", leftMotorSpeed);
        SmartDashboard.putNumber("Random Right Motor Speed", rightMotorSpeed);
        SmartDashboard.putNumber("Set Heading", angle);
      }

      public void randomTurnLeft (ActionQueue actions, int angle) {
        double leftMotorSpeed, rightMotorSpeed;

        rightMotorSpeed = ((int)(Math.random() * (75) + 20))/100;
        leftMotorSpeed = ((int)(Math.random() * ((rightMotorSpeed-.1)*100)))/100;
        actions.addAction(new DriveTowardHeading(leftMotorSpeed, rightMotorSpeed, angle));

        SmartDashboard.putNumber("Random Left Motor Speed", leftMotorSpeed);
        SmartDashboard.putNumber("Random Right Motor Speed", rightMotorSpeed);
        SmartDashboard.putNumber("Set Heading", angle);
      }

      public void newAtonomose (ActionQueue actions) {
        actions.addAction(new DriveStraightTime(.5,.7));
        actions.addAction(new DriveStraightTime(-.5, 1.4));
        Aim(actions);
        FIRE_auto(actions);

      }
}