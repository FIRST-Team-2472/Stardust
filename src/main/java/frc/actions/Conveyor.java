package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class Conveyor extends TimerBase {

    public Conveyor(double lifetime) {
        super(lifetime);
    }

    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "Converying");
        Robot.collector.runConveyor(.75);
    }
	public void periodic() {
    }
	
	public void endAction() {
        Robot.collector.runConveyor(0);
    }
}
