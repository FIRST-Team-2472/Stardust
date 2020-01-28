package frc.actions;

import frc.actions.runners.TimerBase;
import frc.robot.Robot;

public class Wait extends TimerBase {

    public Wait(double lifetime, double givenlifetime) {
    super(lifetime);
    lifetime = givenlifetime;
    }


    @Override
    public void periodic() {

    }

    @Override
    public void endAction() {

    }

}