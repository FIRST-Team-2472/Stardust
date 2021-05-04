package frc.automatic.actions.random;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.TimerBase;
import frc.robot.Robot;

public class Conveyor extends TimerBase {

    public double speed;

	public Conveyor(double lifetime, double givenSpeed) {
        super(lifetime);
        speed = givenSpeed;
    }

    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "Conveyor");
    }
	public void periodic() {
        Robot.collector.runConveyor(speed);
    }
	
	public void endAction() {
        Robot.collector.runConveyor(0);
    }
}