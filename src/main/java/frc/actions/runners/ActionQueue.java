/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.actions.runners;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ActionQueue {
	
	private final Queue<Actionable> steps;
	
	boolean done = true;
	
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
	
	
	public void step() {
		try {
			Actionable action = steps.element();
			
			action.periodic();
						
			
			if (action.isFinished()) {
                System.out.println("next action");

                action.endAction();
				
				steps.remove();
				action = steps.element();
				
				SmartDashboard.putString("ActionName", action.getClass().getSimpleName());
				// Action may override Action name if they are more then their class name
				action.startAction();
			}
			
		} catch (NoSuchElementException e) {
			if (done) {
				System.out.println("Nothing in queue I am done");
				SmartDashboard.putString("ActionName", "Action Queue Done");
				done=false;
			}
		}
	}
	

}
