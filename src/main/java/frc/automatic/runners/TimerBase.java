package frc.automatic.runners;

public abstract class TimerBase implements Actionable{
	
	private long endTime;
	private final double lifetime;
	

	public TimerBase (double getLifetime) {
		lifetime = getLifetime;
	}
	
	@Override
	public void startAction() {
		endTime = System.currentTimeMillis() + (long)(1000 * lifetime);		
	}
	
	@Override
	public abstract void periodic();

	@Override
	public abstract void endAction();

	@Override
	public boolean isFinished() {
		if (System.currentTimeMillis() > endTime) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "Action life:"+lifetime;
		
	}

	

}
