package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Indexer {
    private TalonSRX Indexer;

    public Indexer(int IndexerID) {
        Indexer = new TalonSRX(IndexerID);
    }

    public void runIndexer(double speed) {
        Indexer.set(ControlMode.PercentOutput, speed);
    }

}