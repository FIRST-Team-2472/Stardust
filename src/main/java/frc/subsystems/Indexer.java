package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;


import edu.wpi.first.wpilibj.DoubleSolenoid;


public class Indexer {
    private DoubleSolenoid Indexer;

    public Indexer(int IndexerID, int IndexerID2) {
        Indexer = new DoubleSolenoid(IndexerID, IndexerID2);
    }

    public void runIndexer(double value) {
        Indexer.set(DoubleSolenoid.Value, value);
    }

}