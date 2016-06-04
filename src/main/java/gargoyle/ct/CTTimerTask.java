package gargoyle.ct;

import java.util.TimerTask;

public class CTTimerTask extends TimerTask implements FakeTime {
	private final CTTask task = new CTTask();
	private final CTTaskUpdatable[] updatables;
	private long fakeTime = 0;

	public CTTimerTask(final CTTaskUpdatable... updatables) {
		this.updatables = updatables;
	}

	@Override
	public long getFakeTime() {
		return this.fakeTime;
	}

	public CTTask getTask() {
		return this.task;
	}

	@Override
	public void run() {
		for (final CTTaskUpdatable updatable : this.updatables) {
			updatable.doUpdate(this.task, this.fakeTime == 0 ? CTUtil.currentTimeMillis() : this.fakeTime);
		}
	}

	@Override
	public void setFakeTime(final long fakeTime) {
		this.fakeTime = fakeTime;
	}
}
