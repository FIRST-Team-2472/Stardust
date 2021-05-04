package frc.automatic.actions.random;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.runners.TimerBase;

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