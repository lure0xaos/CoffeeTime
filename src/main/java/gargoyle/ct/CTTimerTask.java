package gargoyle.ct;

import java.awt.Color;
import java.util.TimerTask;

public class CTTimerTask extends TimerTask {
	private final CTTask task = new CTTask();
	private final CTBlocker blocker = new CTBlocker();

	public CTTask getTask() {
		return this.task;
	}

	@Override
	public void run() {
		if (this.task.isReady()) {
			final long currentMillis = CTUtil.currentTimeMillis();
			if (this.task.isBlocked(currentMillis)) {
				this.blocker.setVisible(true);
				this.blocker.setForeground(Color.WHITE);
				this.blocker.setText(
						CTUtil.formatMMSS(CTUtil.timeRemainsTo(currentMillis, this.task.getBlockEnd(currentMillis))));
			}
			if (this.task.isWarn(currentMillis)) {
				this.blocker.setVisible(true);
				this.blocker.setForeground(Color.GREEN);
				this.blocker.setText(
						CTUtil.formatMMSS(CTUtil.timeRemainsTo(currentMillis, this.task.getBlockStart(currentMillis))));
			}
			if (this.task.isSleeping(currentMillis)) {
				this.blocker.setVisible(false);
			}
		}
	}
}
