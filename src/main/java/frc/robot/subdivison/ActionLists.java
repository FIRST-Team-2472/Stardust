package frc.robot.subdivison;

import frc.actions.runners.ActionQueue;
import frc.actions.*;

public class ActionLists {
    public void driveStraight(ActionQueue actions, double distance) {
        actions.addAction(new ZeroEncoder());
        actions.addAction(new Wait(0.01));
        actions.addAction(new DriveStraightVelocity(distance));
        actions.addAction(new Correction(distance));
      }
    
    public void trackDrive(ActionQueue actions) {
        actions.clear();
        actions.addAction(new ZeroIMU());
        actions.addAction(new DriveStraightVelocity(0));
        actions.addAction(new DriveTowardHeading(0.02, .4, 60));
        actions.addAction(new DriveStraightVelocity(1));
        actions.addAction(new Wait(0.5));
        actions.addAction(new DriveTowardHeading(.4, 0, 5));
        actions.addAction(new TurnRobot(0));
        actions.addAction(new DriveStraightVelocity(8.5));
        actions.addAction(new Wait(0.5));
        actions.addAction(new DriveTowardHeading(.4, 0, -60));
        actions.addAction(new DriveStraightVelocity(1.5));
        actions.addAction(new DriveTowardHeading(0.03, .45, 220));
        actions.addAction(new DriveStraightVelocity(4));
        
        actions.addAction(new ZeroIMU());
        actions.addAction(new DriveTowardHeading(.3, 0, -50));
        actions.addAction(new TurnRobot(-50));
        actions.addAction(new DriveStraightVelocity(8.5));
        /*
        actionQueue.addAction(new TurnRobot(180));
        actionQueue.addAction(new DriveStraightVelocity(8.5));
        actionQueue.addAction(new DriveTowardHeading(0.3, 0, 120));
        actionQueue.addAction(new DriveStraightVelocity(2));
        actionQueue.addAction(new DriveTowardHeading(0, .3, 180));
        */
      }
    
      public void boringSlam(ActionQueue actions)
      {
        actions.addAction(new DriveStraightVelocity(3.0));
        actions.addAction(new TurnRobot(90));
        actions.addAction(new DriveStraightVelocity(4.5));
        actions.addAction(new TurnRobot(0));
        actions.addAction(new DriveStraightVelocity(16.5));
        actions.addAction(new TurnRobot(-90));
    
        actions.addAction(new DriveStraightVelocity(4.0));
        actions.addAction(new TurnRobot(0));
        actions.addAction(new DriveStraightVelocity(4.5));
        actions.addAction(new TurnRobot(90));
        actions.addAction(new DriveStraightVelocity(4.5));
        actions.addAction(new TurnRobot(180));
        actions.addAction(new DriveStraightVelocity(4.5));
        actions.addAction(new TurnRobot(270));
        actions.addAction(new DriveStraightVelocity(4.5));
        actions.addAction(new TurnRobot(180));
        actions.addAction(new DriveStraightVelocity(16.0));
        actions.addAction(new TurnRobot(90));
        actions.addAction(new DriveStraightVelocity(4.0));
        actions.addAction(new TurnRobot(180));
        actions.addAction(new DriveStraightVelocity(3.0));
      }
    
      public void zigZag(ActionQueue actions) {
        actions.clear();
        actions.addAction(new DriveTowardHeading(.6, .4, -20));
        actions.addAction(new DriveTowardHeading(.4, .6, 20));
        actions.addAction(new DriveTowardHeading(.6, .4, -20));
        actions.addAction(new DriveTowardHeading(.4, .6, 20));
        actions.addAction(new DriveTowardHeading(.6, .4, -20));
        actions.addAction(new DriveTowardHeading(.4, .6, 20));
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
        actions.addAction(new Aim());
        actions.addAction(new Wait(5));
      }
    
      public void FIRE(ActionQueue actions) {
        actions.clear();
        actions.addAction(new Aim());
        actions.addAction(new RangeFinding());
        actions.addAction(new ShieldAim());
        actions.addAction(new Shooting(5));
      }
    
      public void runBarrelRun(ActionQueue actions) {
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(11.0));
        actions.addAction(new TurnRobot(-90.0));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(3.0));
        actions.addAction(new TurnRobot(-180.0));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(3.0));
        actions.addAction(new TurnRobot(-270.0));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(3.0));
        actions.addAction(new TurnRobot(-359.0));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(3.0));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(9.0));
        actions.addAction(new TurnRobot(-270));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(5.5));
        actions.addAction(new TurnRobot(-180));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(3.5));
        actions.addAction(new TurnRobot(-90));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(3.0));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(7.0));
        actions.addAction(new TurnRobot(-1));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(8.0));
        actions.addAction(new TurnRobot(90));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(4.0));
       //hope
        actions.addAction(new TurnRobot(180));
        actions.addAction(new ZeroCounters());
        actions.addAction(new Wait(0.1));
    
        actions.addAction(new DriveStraightVelocity(26.0));
      }
    
      public void runBounceCourse(ActionQueue actions) {
        actions.addAction(new DriveStraightVelocity(2.75));
        actions.addAction(new TurnRobot(90));
        actions.addAction(new ZeroCounters());
        actions.addAction(new DriveStraightVelocity(2.75));
        actions.addAction(new TurnRobot(-150));
        actions.addAction(new ZeroCounters());
        actions.addAction(new DriveStraightVelocity(10));
        actions.addAction(new TurnRobot(60));
        actions.addAction(new ZeroCounters());
        actions.addAction(new DriveStraightVelocity(2.5));
        actions.addAction(new TurnRobot(85));
        actions.addAction(new ZeroCounters());
        actions.addAction(new DriveStraightVelocity(9));
        actions.addAction(new TurnRobot(180));
        actions.addAction(new ZeroCounters());
        actions.addAction(new DriveStraightVelocity(9));
        actions.addAction(new TurnRobot(80));
        actions.addAction(new ZeroCounters());
        actions.addAction(new DriveStraightVelocity(5.5));
        actions.addAction(new TurnRobot(85));
        actions.addAction(new ZeroCounters());
        actions.addAction(new DriveStraightVelocity(8.50));
        actions.addAction(new TurnRobot(-160));
        actions.addAction(new ZeroCounters());
        actions.addAction(new DriveStraightVelocity(3.0));
        actions.addAction(new TurnRobot(90));
        actions.addAction(new ZeroCounters());
        actions.addAction(new DriveStraightVelocity(2.5));
      }
}