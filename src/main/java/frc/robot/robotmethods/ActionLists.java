package frc.robot.robotmethods;

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
        actions.addAction(new Correction(distance));
      }
    
      public void loadBallsAuto(ActionQueue actions) {
        actions.addAction(new DriveStraightTime(.5, 5));
        actions.addAction(new DumpBalls(3));
      }
    
      public void seekTheFleshMeat(ActionQueue actions) {
        actions.addAction(new TurnRobot(180));
        actions.addAction(new Wait(5));
        actions.addAction(new Seek());
        actions.addAction(new Wait(5));
        actions.addAction(new AimHorizontally());
        actions.addAction(new Wait(5));
      }
    
      public void FIRE(ActionQueue actions) {
        actions.addAction(new AimHorizontally());
        actions.addAction(new RangeFinding());
        //actions.addAction(new ShieldAim());
        actions.addAction(new StartFlyWheel(1.5));
        //actions.addAction(new Shooting(5));
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




    


}