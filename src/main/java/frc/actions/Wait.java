package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class Wait extends TimerBase {

    public Wait(double givenlifetime) {
        super(givenlifetime);
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