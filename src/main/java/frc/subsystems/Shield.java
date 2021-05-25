package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Shield {
    private TalonSRX Shield;

    public Shield(int ShieldID) {
        Shield = new TalonSRX(ShieldID);
        Shield.setInverted(false);

        Shield.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        Shield.setSensorPhase(true);
        //Shield.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,100000);
        Shield.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen,0);
    }

    public void runShieldVelocity(double speed) {
        Shield.set(ControlMode.Velocity, speed * 6250);
    }

    public void runShieldPower(double power) {
        Shield.set(ControlMode.PercentOutput, power);
    }

    public int getShieldSpeed() {
        return (int)Shield.getSelectedSensorVelocity();
    }

    public double getShieldDistance() {
        return Shield.getSelectedSensorPosition();
    }

    public void zeroCounters() {
        Shield.setSelectedSensorPosition(0);
        Shield.configForwardSoftLimitEnable(true);
        Shield.configForwardSoftLimitThreshold(430000);
        Shield.configReverseSoftLimitEnable(false);

    }

    public boolean getLimitSwitchOn() {
        return Shield.getSensorCollection().isRevLimitSwitchClosed();
    }

    public void resetEncoders() {
    
    }
}
