package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Shield {
    private TalonSRX Shield;

    public Shield(int ShieldID) {
        Shield = new TalonSRX(ShieldID);

        Shield.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        Shield.setSensorPhase(true);
    }

    public void runShieldVelocity(double speed) {
        Shield.set(ControlMode.Velocity, speed * 300);
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
    }
}