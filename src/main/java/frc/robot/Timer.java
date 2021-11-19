package frc.robot;
// Needs imports

public class Timer {
    private long end;

    public Timer(double seconds) {
        end = System.currentTimeMillis() + (long)(1000 * seconds);
    }

    public boolean isTimedOut() {
        return end <= System.currentTimeMillis();
    }

    public static boolean tryIsTimedOut(Timer it) {
        if (it == null) {
            return false;
        }
        return it.isTimedOut();
    }

    public void reset() {
        end = end + System.currentTimeMillis();
    }

    public void reset(double seconds) {
        end = System.currentTimeMillis() + (long)(1000 * seconds);
    }
}