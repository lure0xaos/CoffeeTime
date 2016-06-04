package gargoyle.ct;

import java.util.Timer;

public class CTTimer {
	private final Timer timer;
	private final CTTimerTask timerTask;

	public CTTimer(final CTTaskUpdatable... updatables) {
		this.timer = new Timer(CTTimer.class.getName(), true);
		this.timerTask = new CTTimerTask(updatables);
		this.timer.scheduleAtFixedRate(this.timerTask, 100, 500);
	}

	public void arm(final CTConfig config) {
		final CTTask task = this.timerTask.getTask();
		task.setConfig(config);
		task.setStarted(CTUtil.currentTimeMillis());
	}

	public void terminate() {
		this.timer.cancel();
	}

	public void unarm() {
		final CTTask task = this.timerTask.getTask();
		task.setStarted(0);
	}
}
