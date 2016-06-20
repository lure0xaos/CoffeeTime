package gargoyle.ct;

import java.util.concurrent.TimeUnit;

public class CTTask {
	private CTConfig config;
	private long started;

	public long getBlockEnd(final long currentMillis) {
		return CTTimeUtil.upTo(CTTimeUtil.toBase(this.getStarted(), currentMillis, this.getConfig().getWhole()),
				this.getConfig().getWhole());
	}

	public long getBlockStart(final long currentMillis) {
		return this.getBlockEnd(currentMillis) - this.getConfig().getBlock();
	}

	public CTConfig getConfig() {
		return this.config;
	}

	public long getCycleStart(final long currentMillis) {
		return CTTimeUtil.downTo(CTTimeUtil.toBase(this.getStarted(), currentMillis, this.getConfig().getWhole()),
				this.getConfig().getWhole());
	}

	public long getStarted() {
		return this.started;
	}

	public long getStarted(final TimeUnit unit) {
		return CTTimeUtil.fromMillis(unit, this.started);
	}

	public long getWarnStart(final long currentMillis) {
		return this.getBlockStart(currentMillis) - this.getConfig().getWarn();
	}

	public boolean isBlocked(final long currentMillis) {
		if (!this.isReady()) {
			return false;
		}
		return CTTimeUtil.isBetween(currentMillis, this.getBlockStart(currentMillis), this.getBlockEnd(currentMillis));
	}

	public boolean isReady() {
		return (this.getStarted() != 0) && (this.getConfig() != null);
	}

	public boolean isSleeping(final long currentMillis) {
		return CTTimeUtil.isBetween(currentMillis, this.getCycleStart(currentMillis), this.getWarnStart(currentMillis));
	}

	public boolean isWarn(final long currentMillis) {
		if (!this.isReady()) {
			return false;
		}
		return CTTimeUtil.isBetween(currentMillis, this.getWarnStart(currentMillis), this.getBlockStart(currentMillis));
	}

	public void setConfig(final CTConfig config) {
		this.config = config;
	}

	public void setStarted(final long started) {
		this.started = started;
	}

	public void setStarted(final TimeUnit unit, final long started) {
		this.started = CTTimeUtil.toMillis(unit, started);
	}
}
