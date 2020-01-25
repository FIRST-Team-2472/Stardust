/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.actions.runners;


/**
 * Add your docs here.
 */
public class JoinActions implements Actionable{

    private final Actionable[] actions;

    public JoinActions() {
        actions = new Actionable[]{new NullAction()};
    }

    public JoinActions(Actionable... actions) {
        this.actions = actions;
    }

    @Override
    public void startAction() {
        for (Actionable action: actions) {
            action.startAction();
        }
    }

    @Override
    public void periodic() {
        for (Actionable action: actions) {
            action.startAction();
        }
    }

    @Override
    public void endAction() {
        for (Actionable action: actions) {
            action.startAction();
        }
    }

    @Override
    public boolean isFinished() {
        boolean finished = true;
        for (Actionable action: actions) {
            if (!action.isFinished()) {
                finished = false;
            }
        }
        return finished;
        // I <3 functional programming
        //return Arrays.stream(actions).map(action -> action.isFinished()).allMatch(resultBool -> resultBool);
    }
}

