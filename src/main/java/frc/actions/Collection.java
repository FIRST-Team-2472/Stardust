package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;
import frc.subsystems.Collector;

public class Collection extends TimerBase {

	public Collection(double lifetime) {
        super(lifetime);
        // TODO Auto-generated constructor stub
    }

    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "Grabbin the boomers");
        Robot.collector.runFrontWheels(1);
    }
	public void periodic() {
        Robot.collector.runFrontWheels(1);
    }
	
	public void endAction() {
	
    }
}
