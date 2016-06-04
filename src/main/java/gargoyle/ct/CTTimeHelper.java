package gargoyle.ct;

public class CTTimeHelper implements TimeHelper {
	private long fakeTime;
	private long delta = 0;

	public CTTimeHelper() {
	}

	@Override
	public long currentTimeMillis() {
		return CTUtil.currentTimeMillis() + this.delta;
	}

	@Override
	public long getFakeTime() {
		return this.fakeTime;
	}

	@Override
	public void setFakeTime(final long fakeTime) {
		this.fakeTime = fakeTime;
		this.delta = fakeTime - CTUtil.currentTimeMillis();
	}
}
