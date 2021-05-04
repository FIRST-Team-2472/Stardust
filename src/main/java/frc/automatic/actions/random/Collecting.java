package frc.automatic.actions.random;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.TimerBase;
import frc.robot.Robot;

public class Collecting extends TimerBase {

	public Collecting(double lifetime) {
        super(lifetime);
    }

    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "Collecting");
        Robot.collector.runFrontWheels(-1);
        Robot.collector.runConveyor(1);
    }
    
	public void periodic() {
    }
	
	public void endAction() {
        Robot.collector.runFrontWheels(0);
        Robot.collector.runConveyor(0);
    }
}
