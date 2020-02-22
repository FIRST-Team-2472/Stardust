package frc.robot;

public class SetTimeout {
    public static void setTimeout(Runnable r, double seconds) {
        new Thread(() -> {
            try {
                Thread.sleep((long) (1000 * seconds));
            } catch (Exception e) {
            }
            r.run();
        });
    }
}