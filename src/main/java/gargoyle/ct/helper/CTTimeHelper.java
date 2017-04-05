package gargoyle.ct.helper;

import gargoyle.ct.util.CTTimeUtil;

public class CTTimeHelper implements TimeHelper {
    private long delta;
    private long fakeTime;

    @Override
    public long currentTimeMillis() {
        return CTTimeUtil.currentTimeMillis() + delta;
    }

    @Override
    public long getFakeTime() {
        return fakeTime;
    }

    @Override
    public void setFakeTime(long fakeTime) {
        this.fakeTime = fakeTime;
        delta = fakeTime - CTTimeUtil.currentTimeMillis();
    }
}
