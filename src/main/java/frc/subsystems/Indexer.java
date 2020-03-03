package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**  This fun little subsytems moves balls from the convery belt to the shooter flywheel for shooting*/
public class Indexer {
    private DoubleSolenoid Indexer;

    public Indexer(int IndexerID, int IndexerID2) {
        Indexer = new DoubleSolenoid(IndexerID, IndexerID2);
    }

    public void runIndexerForward() {
        Indexer.set(DoubleSolenoid.Value.kForward);
    }
    
    public void runIndexerBackward() {
        Indexer.set(DoubleSolenoid.Value.kReverse);
    }

    public void runIndexerOff() {
        Indexer.set(DoubleSolenoid.Value.kOff);
    }

}