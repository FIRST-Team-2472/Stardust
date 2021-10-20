package frc.robot.robotmethods;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.automatic.actions.extras.SetShield;
import frc.automatic.actions.extras.Wait;
import frc.automatic.actions.shooting.Shoot;
import frc.automatic.actions.shooting.StartFlyWheel;
import frc.automatic.runners.ActionQueue;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID;

public class TeleopMethods {
    int driveState, cooldown;
    boolean teleopShooting;

    public void initialize(ActionQueue teleopActions) {
        Robot.turret.zeroTurret();
        Robot.drive.zeroCounters();
        Robot.collector.pushoutfrontwheel();
        Robot.compressor.setClosedLoopControl(true);
        teleopActions.addAction(new SetShield());

        SmartDashboard.putString("RobotState", "TeleopEnabled");

        driveState = 0;
        cooldown = 0;
    }

    public void driveTrain() {
        // switches drive state from tank to acrade drive
        if (Robot.leftJoystick.getRawButtonPressed(11)) {
            driveState++;
            if (driveState > 1) {
                driveState = 0;
            }
        }

        if (driveState == 0) {
            // runs arcade drive
            Robot.drive.arcadeDriveVelocity(Robot.leftJoystick.getY() * -.5, Robot.leftJoystick.getX() * -.5);
            SmartDashboard.putString("Drive State", "Arcade");
        }
        else if (driveState == 1) {
            // runs tank drive
            Robot.drive.tankDriveVelocity(Robot.leftJoystick.getY() * -.5, Robot.rightJoystick.getY() * -.5);
            SmartDashboard.putString("Drive State", "Tonk ;)");
        }
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

    public void runCollector() {
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

    public void shooting(ActionQueue teleopActions) {
        if (Robot.xboxcontroller.getAButtonPressed() && Robot.limelight.isTargetSpotted()) Robot.actionList.Aim(teleopActions);

        if (Robot.xboxcontroller.getXButtonPressed()) //Robot.actionList.FIRE_telop(teleopActions);
        {
            teleopActions.addAction(new Wait(1));
            teleopActions.addAction(new StartFlyWheel(2));
            teleopActions.addAction(new Shoot());
        }

        if (Robot.xboxcontroller.getBButtonPressed()) teleopActions.clear();
    }

    public void manualFire(ActionQueue teleopActions) {
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
        // pushes out pistons to collect balls
        if (Robot.xboxcontroller.getBumper(GenericHID.Hand.kRight)) Robot.collector.pushoutfrontwheel();
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
