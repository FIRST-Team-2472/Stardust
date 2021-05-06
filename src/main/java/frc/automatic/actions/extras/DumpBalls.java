package frc.automatic.actions.extras;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.TimerBase;
import frc.robot.Robot;

public class DumpBalls extends TimerBase {

	public DumpBalls(double lifetime) {
        super(lifetime);
    }

    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "Collecting");
        Robot.collector.runConveyorPower(.7);
    }
	public void periodic() {
    }
	
	public void endAction() {
        Robot.collector.runFrontWheels(0);
    }
}
