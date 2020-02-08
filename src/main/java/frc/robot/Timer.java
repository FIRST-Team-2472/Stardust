package frc.robot;

public class Timer {
    private final long end;

    public Timer(double seconds) {
        end = System.currentTimeMillis() + (long)(1000 * seconds);
    }

    public boolean isTimedOut() {
        return end <= System.currentTimeMillis();
    }
}