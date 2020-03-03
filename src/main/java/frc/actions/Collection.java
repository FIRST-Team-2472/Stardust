package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class Collection extends TimerBase {

	public Collection(double lifetime) {
        super(lifetime);
    }

    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "Collecting");
        Robot.collector.runFrontWheels(1);
    }
	public void periodic() {
    }
	
	public void endAction() {
        Robot.collector.runFrontWheels(0);
    }
}
