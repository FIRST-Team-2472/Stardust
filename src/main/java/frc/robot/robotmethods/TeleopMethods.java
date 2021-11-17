package frc.robot.robotmethods;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.automatic.runners.ActionQueue;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID;

public class TeleopMethods {

    public void initialize(ActionQueue teleopActions) {
        //zeros encoders
        Robot.drive.zeroCounters();

        //pushes out the front pistons
        Robot.collector.pushoutfrontwheel();

        //turns on the compresser
        Robot.compressor.setClosedLoopControl(true);

        //turns off the limelight leds (theoreticly)
        Robot.limelight.setDriverCamMode(true);

        //moves the shield down to zero its encoder. In compition it is done in autnomous
        //teleopActions.addAction(new SetShield());
        SmartDashboard.putString("RobotState", "TeleopEnabled");
    }

    public void driveTrain() {
        //sets the robot to drive with the left joystick based on their
        Robot.drive.arcadeDriveVelocity(Robot.leftJoystick.getY() * -.5, Robot.leftJoystick.getX() * -.5);
        SmartDashboard.putString("Drive State", "Arcade");
    }

    public void runTurret() {
        // runs the turret
        if (Robot.xboxcontroller.getTriggerAxis(GenericHID.Hand.kRight) > .6)
            Robot.turret.runTurret(-.15);
        else if (Robot.xboxcontroller.getTriggerAxis(GenericHID.Hand.kLeft) > .6)
            Robot.turret.runTurret(.15);
        else
            Robot.turret.runTurret(0);
    }

    public void runCollector(ActionQueue teleopActions) {
        if (!teleopActions.isInProgress()) {
            if (Math.abs(Robot.xboxcontroller.getRawAxis(1)) > Math.abs(Robot.xboxcontroller.getRawAxis(5))) {
                // runs the intake for the lower elvator and collector wheels
                Robot.collector.runConveyorPower(.3 * -Math.abs(Robot.xboxcontroller.getRawAxis(1)));
                Robot.collector.runFrontWheels(.5 * -Math.abs(Robot.xboxcontroller.getRawAxis(1)));
            } else {
                // runs the outtake for the lower elvator and collector wheels
                Robot.collector.runConveyorPower(.3 * Math.abs(Robot.xboxcontroller.getRawAxis(5)));
                Robot.collector.runFrontWheels(.5 * Math.abs(Robot.xboxcontroller.getRawAxis(5)));
            }
        }
    }

    public void shooting(ActionQueue teleopActions) {
        //starts the automatic process of aiming at the target
        if (Robot.xboxcontroller.getAButtonPressed()) {
            Robot.actionList.Aim(teleopActions);
        }

        //starts the automatic process of firing at the target
        if (Robot.xboxcontroller.getXButtonPressed()) Robot.actionList.FIRE_telop(teleopActions);

        //cancels all actions in the actionqueue
        if (Robot.xboxcontroller.getBButtonPressed()) teleopActions.abortShooter();
    }

    public void manualFire(ActionQueue teleopActions) {
        //automaticly runs the shield up and down
        //probaldy souldn't ever use
        if (Robot.xboxcontroller.getPOV() == 0 || Robot.xboxcontroller.getPOV() == 45
                || Robot.xboxcontroller.getPOV() == 315) {
            Robot.shield.runShieldPower(.1);
        } else if (Robot.xboxcontroller.getPOV() == 180 || Robot.xboxcontroller.getPOV() == 135
                || Robot.xboxcontroller.getPOV() == 225) {
            Robot.shield.runShieldPower(-.1);
        } else {
            Robot.shield.runShieldPower(0);
        }
    }

    public void moveFrontWheels() {
        //pushes out pistons to collect balls
        if (Robot.xboxcontroller.getBumper(GenericHID.Hand.kRight)) Robot.collector.pushoutfrontwheel();
        //pushes in the pistons
        //only used at the end of competion NOT during
        if (Robot.leftJoystick.getRawButtonPressed(11)) Robot.collector.pushinfrontwheel();
    }

    public void runClimber() {
        // would run climber if we had one
        if (Robot.leftJoystick.getRawButtonPressed(3) && Robot.rightJoystick.getRawButton(3)) {
            Robot.climb.runClimber(1);
        } else {
            Robot.climb.runClimber(0);
        }

        if (Robot.leftJoystick.getRawButton(2) && Robot.rightJoystick.getRawButton(2)) {
            Robot.climb.runClimber(-1);
        } else {
            Robot.climb.runClimber(0);
        }
    }
}
