package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class LogitechController extends GenericHID {

    public LogitechController(int port) {
        super(port);
    }

    @Override
    public double getX(Hand hand) {
        switch (hand) {
        case kLeft:
            return this.getRawAxis(0);
        case kRight:
            return this.getRawAxis(2);
        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public double getY(Hand hand) {
        switch (hand) {
        case kLeft:
            return this.getRawAxis(1);
        default:
            throw new IllegalArgumentException();
        }
    }

    public boolean getXButton() {
        return this.getRawButton(1);
    }

    public boolean getYButton() {
        return this.getRawButton(4);
    }

    public boolean getAButton() {
        return this.getRawButton(2);
    }

    public boolean getBButton() {
        return this.getRawButton(3);
    }

    public boolean getXButtonPressed() {
        return this.getRawButtonPressed(1);
    }

    public boolean getYButtonPressed() {
        return this.getRawButtonPressed(4);
    }

    public boolean getAButtonPressed() {
        return this.getRawButtonPressed(2);
    }

    public boolean getBButtonPressed() {
        return this.getRawButtonPressed(3);
    }

    public boolean getBumper(Hand hand) {
        switch (hand) {
        case kLeft:
            return getRawButton(5);
        case kRight:
            return getRawButton(6);
        default:
            throw new IllegalArgumentException();
        }

    }

}