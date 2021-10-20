/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.automatic.runners;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class ActionQueue {
	
	private Queue<Actionable> steps;
	
	boolean inProgress = true;
	
	public ActionQueue() {
		steps = new ArrayDeque<Actionable>();
		addAction(new NullAction());
	}

	public void clear() {
		steps.clear();
		addAction(new NullAction());
	}
	
	public void addAction(Actionable action) {
		steps.add(action);
	}
	
	public void abortShooter() {
		try 
		{
			steps.element().endAction();
			Robot.shooter.runFlyWheelPower(0);
			Robot.elevator.runElevatorPower(0);
		} catch (NoSuchElementException e) {};
		clear();
	}
	
	public boolean step() {
		try {
			Actionable action = steps.element();
			
			action.periodic();

			inProgress = true;	
			
			if (action.isFinished()) {
                System.out.println("next action");

                action.endAction();
				
				steps.remove();
				action = steps.element();
				System.out.println("named: " +  action.getClass().getSimpleName());
				SmartDashboard.putString("ActionName", action.getClass().getSimpleName());
				// Action may override Action name if they are more then their class name
				action.startAction();
			}
			return false;
			
		} catch (NoSuchElementException e) {
				System.out.println("Nothing in queue I am done");
				SmartDashboard.putString("ActionName", "Action Queue Done");
				inProgress = false;
				clear();
			return true;
		}
	}

	public boolean isInProgress() {
		return inProgress;
	}
}
