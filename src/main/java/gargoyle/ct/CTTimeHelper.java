package gargoyle.ct;

public class CTTimeHelper implements TimeHelper {
    private long delta = 0;
    private long fakeTime;

    public CTTimeHelper() {
    }

    @Override
    public long currentTimeMillis() {
        return CTTimeUtil.currentTimeMillis() + this.delta;
    }

    @Override
    public long getFakeTime() {
        return this.fakeTime;
    }

    @Override
    public void setFakeTime(final long fakeTime) {
        this.fakeTime = fakeTime;
        this.delta = fakeTime - CTTimeUtil.currentTimeMillis();
    }
}
