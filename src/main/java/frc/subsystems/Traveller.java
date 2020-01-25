package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Traveller {
    private TalonSRX TravellerR;
    private TalonSRX TravellerL;

    public Traveller(int TravellerRID, int TravellerLID) {
        TravellerL = new TalonSRX(TravellerRID);
        TravellerR = new TalonSRX(TravellerLID);
    }

    public void runTraveller(double speed) {
        runTravellerR(speed);
        runTravellerL(speed);
    }

    public void runTravellerR(double speed) {
        TravellerR.set(ControlMode.PercentOutput, speed);
    }

    public void runTravellerL(double speed) {
        TravellerL.set(ControlMode.PercentOutput, speed);
    }
}