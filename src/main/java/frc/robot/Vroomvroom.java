package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

// This is an xbox controller
public class Vroomvroom extends XboxController {
    public boolean isCool = true;
    public Vroomvroom(int number) {
        super(number);
    }
}