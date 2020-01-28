package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class Wait extends TimerBase {

    public Wait(double lifetime, double givenlifetime) {
    super(lifetime);
    lifetime = givenlifetime;
    }

    @Override
    public void startAction() {
        super.startAction();
        SmartDashboard.putString("ActionName", "Waiting for electric boogaloo 2");
    }

    @Override
    public void periodic() {

    }

    @Override
    public void endAction() {

    }

}