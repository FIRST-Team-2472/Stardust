package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret {
    
    private TalonSRX turret;
    
    public Turret(int turretID) {
        turret = new TalonSRX(turretID);

        turret.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        turret.setSelectedSensorPosition(0);

        turret.setSensorPhase(true);
    }

    
    public void runTurret(double speed) {
        turret.set(ControlMode.PercentOutput, speed);
        SmartDashboard.putNumber("TurretSpeed", speed);
    }

    public void setTurretDistance(int dis) {
        turret.setSelectedSensorPosition(dis);
    }

    public double getTurretDistance() {
       return turret.getSelectedSensorPosition();
    }

    public void zeroTurret() {
        turret.setSelectedSensorPosition(0);
        turret.configForwardSoftLimitEnable(true);
        turret.configForwardSoftLimitThreshold(200000);
        turret.configReverseSoftLimitEnable(true);
        turret.configReverseSoftLimitThreshold(-200000);
    }
}

