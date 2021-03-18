package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Robot;

public class Shooter {
  private final TalonSRX flyWheel;

    public Shooter(final int FlyWheelID) {
        flyWheel = new TalonSRX(FlyWheelID);
        
    }

    public void setupMotionMagic(final double f, final double p, final double i, final double d, final int velocity, final int acceleration) {
      // frontRight.setInverted(true);
      // backRight.setInverted(true);

      flyWheel.configFactoryDefault();

      // Tell the talon that he has a quad encoder
      flyWheel.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);

      // Set minimum output (closed loop) to 0 for now
      flyWheel.configNominalOutputForward(0, 30);
      flyWheel.configNominalOutputReverse(0, 30);

      // Set maximum forward and backward to full speed
      flyWheel.configPeakOutputForward(1, 30);
      flyWheel.configPeakOutputReverse(-1, 30);

      // Motion magic cruise (max speed) is 100 counts per 100 ms
      flyWheel.configMotionCruiseVelocity(velocity, 30);

      // Motion magic acceleration is 50 counts
      flyWheel.configMotionAcceleration(acceleration, 30);

      // Zero the sensor once on robot boot up
      flyWheel.setSelectedSensorPosition(0, 0, 30);

      flyWheel.config_kP(0, p);
      flyWheel.config_kI(0, i);
      flyWheel.config_kD(0, d);
      flyWheel.config_kF(0, f);
    }

    public void runFlyWheel(final double speed) {
      flyWheel.set(ControlMode.PercentOutput, speed);    
    }

    public void fireFlyWheel() {
      flyWheel.set(ControlMode.PercentOutput, Robot.limelight.distanceIN()  /* TODO make speed of flywheel effected by distance from target*/);
    }

    public double runSensorVelocity() {
      return flyWheel.getSelectedSensorVelocity();
    }
    // TODO Write a set RPM method
    
}