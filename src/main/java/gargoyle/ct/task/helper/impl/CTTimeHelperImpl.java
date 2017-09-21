package gargoyle.ct.task.helper.impl;

import gargoyle.ct.task.helper.CTTimeHelper;
import gargoyle.ct.util.CTTimeUtil;

public class CTTimeHelperImpl implements CTTimeHelper {
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
