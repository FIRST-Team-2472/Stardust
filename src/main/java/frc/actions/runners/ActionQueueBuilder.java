/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.actions.runners;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ActionQueueBuilder {
	
	private final List<Actionable> steps;
	
	public ActionQueueBuilder() {
		steps = new ArrayList<Actionable>();
		steps.add(new NullAction());
	}

	
	public ActionQueueBuilder add(Actionable action) {
		steps.add(action);
		return this;
	}

	public ActionQueue build() {
		return new ActionQueue(steps);
	}

}
