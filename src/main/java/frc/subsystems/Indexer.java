package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Indexer {
    private TalonSRX IndexerR;
    private TalonSRX IndexerL;

    public Indexer (int IndexerRID, int IndexerLID){
        IndexerL = new TalonSRX(IndexerRID);
        IndexerR = new TalonSRX(IndexerLID);
    }

    public void runIndexer(double speed){
        runIndexerR(speed);
        runIndexerL(speed);
    }

    public void runIndexerR(double speed) {
        IndexerR.set(ControlMode.PercentOutput, speed);
    }

    public void runIndexerL(double speed) {
        IndexerL.set(ControlMode.PercentOutput, speed);
    }
}