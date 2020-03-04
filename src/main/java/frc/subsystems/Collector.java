package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/** Subsystem that deals with ball collection with front wheels that can move
 *  outside the frame peritior and a conveyor belt/elevator to move balls to 
 *  the middle of the robot
 */
public class Collector {
    private final TalonSRX conveyor;
    private final TalonSRX frontWheels;
    private final DoubleSolenoid frontWheelPush;


    /**
     * Constructs the Collector class
     * @param conveyorID talonSRX ID for conveyor
     * @param frontWheelsID talonSRX ID for front wheels
     * @param frontWheelForwardID pcm channel ID for pushing out
     * @param frontWheelBackID pcm channel ID for pushing back in
     */
    public Collector (int conveyorID, int frontWheelsID, int frontWheelForwardID, int frontWheelBackID) {
        conveyor = new TalonSRX(conveyorID);
        frontWheels = new TalonSRX(frontWheelsID);
        frontWheelPush = new DoubleSolenoid(16, frontWheelForwardID, frontWheelBackID);
        conveyor.setInverted(true);
    }

    /**
     * Runs both the front wheels and the conveyor to do ball collection
     * This runs the front wheels reverse to suck in balls
     * @param speed [-1.0, 1.0] value to run the motor at
     */
    public void runCollector(double speed) {
        runFrontWheels(-speed);
        runConveyor(speed);
    }

    /**
     * Runs the wheels infront of the robot
     * Negative number intake balls
     * @param speed [-1.0, 1.0] value to run the motor at
     */
    public void runFrontWheels(double speed) {
        frontWheels.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Runs the conveyor in the robot
     * Positive numbers intake balls
     * @param speed [-1.0, 1.0] value to run the motor at
     */
    public void runConveyor(double speed) {
        conveyor.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Pushes the front wheels outside the frame permitor for better collection
     */
    public void pushoutfrontwheel() {
        frontWheelPush.set(Value.kForward);
    }

    /**
     * Pushes the front wheels back inside the frame permitor for fun
     */
    public void pushofffrontwheel() {
        frontWheelPush.set(Value.kOff);
    }

    /**
     * Turns off the solonoid to give it a break if the pistion is in the 
     * correct position
     */
    public void pushinfrontwheel() {
        frontWheelPush.set(Value.kReverse);
    }
}