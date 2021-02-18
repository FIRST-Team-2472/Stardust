package frc.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.actions.runners.Actionable;
import frc.robot.Robot;


public class TurnRobot implements Actionable {
    private final int angle;

    
    public TurnRobot(int angle){
        this.angle = angle;
    }
    

    @Override
    public void startAction() {
        SmartDashboard.putString("ActionName", "Turn");
    }



    @Override
    public void periodic() {
        /*
            we are at 0 want to go to 90
            90 - 0 =90
            left = 90*.5 = 45
        
        double error =  angle - Robot.imu.getAngle();
        double kP = .5;
        Robot.drive.tankDriveVelocity(error*kP, error*-kP);*/
    }

    @Override
    public void endAction() {
        Robot.drive.tankDriveVelocity(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false; 
        //Math.abs(angle - Robot.imu.getAngle()) < 2;
    }

}