package frc.actions.templates;

import frc.actions.runners.Actionable;

public class NormalAction implements Actionable{

    public NormalAction() {}

    @Override
    public void startAction() {
    }

    @Override
    public void periodic() {}

    @Override
    public void endAction() {}

    @Override
    public boolean isFinished() {
        return true;
    }
}