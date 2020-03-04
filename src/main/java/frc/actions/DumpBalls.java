package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class DumpBalls extends TimerBase {

	public DumpBalls(double lifetime) {
        super(lifetime);
    }

    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "Collecting");
        Robot.collector.runConveyor(1);
    }
	public void periodic() {
    }
	
	public void endAction() {
        Robot.collector.runConveyor(0);
    }
}
