package frc.actions;

import javax.swing.Action;
import javax.swing.ActionMap;

import frc.actions.runners.ActionQueue;
import frc.actions.runners.Actionable;
import frc.robot.Robot;

public class Seek implements Actionable {

    @Override
    public void startAction() {

    }


    @Override
    public void periodic() {
        Robot.turret.runTurret(-0.2);
    }

    @Override
    public void endAction() {
        Robot.turret.runTurret(0);
    }

    @Override
    public boolean isFinished() {
  
    return (Robot.limelight.isTargetSpotted());
  

}
}
