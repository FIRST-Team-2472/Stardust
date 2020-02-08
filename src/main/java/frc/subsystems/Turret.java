package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret {
    
    private TalonSRX turret;
    
    public Turret(int turretID) {
        turret = new TalonSRX(turretID);
    }

    public void runTurret(double speed) {
        turret.set(ControlMode.PercentOutput, speed);
        SmartDashboard.putNumber("TurretSpeed", speed);
    }

}