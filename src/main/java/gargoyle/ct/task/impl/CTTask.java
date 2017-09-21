package gargoyle.ct.task.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.util.CTTimeUtil;

import java.util.concurrent.TimeUnit;

public class CTTask {

    private CTConfig config;
    private long started;

    public CTConfig getConfig() {
        return config;
    }

    public void setConfig(CTConfig config) {
        this.config = config;
    }

    public long getStarted() {
        return started;
    }

    public void setStarted(long started) {
        this.started = started;
    }

    public long getStarted(TimeUnit unit) {
        return CTTimeUtil.fromMillis(unit, started);
    }

    public boolean isBlocked(long currentMillis) {
        return isReady() &&
                CTTimeUtil.isBetween(currentMillis, getBlockStart(currentMillis), getBlockEnd(currentMillis));
    }

    public long getBlockStart(long currentMillis) {
        return getBlockEnd(currentMillis) - config.getBlock();
    }

    public long getBlockEnd(long currentMillis) {
        return CTTimeUtil.upTo(CTTimeUtil.toBase(started, currentMillis, config.getWhole()), config.getWhole());
    }

    public boolean isReady() {
        return started != 0 && config != null;
    }

    public boolean isSleeping(long currentMillis) {
        return CTTimeUtil.isBetween(currentMillis, getCycleStart(currentMillis), getWarnStart(currentMillis));
    }

    private long getCycleStart(long currentMillis) {
        return CTTimeUtil.downTo(CTTimeUtil.toBase(started, currentMillis, config.getWhole()), config.getWhole());
    }

    private long getWarnStart(long currentMillis) {
        return getBlockStart(currentMillis) - config.getWarn();
    }

    public boolean isWarn(long currentMillis) {
        return isReady() &&
                CTTimeUtil.isBetween(currentMillis, getWarnStart(currentMillis), getBlockStart(currentMillis));
    }

    public void setStarted(TimeUnit unit, long started) {
        this.started = CTTimeUtil.toMillis(unit, started);
    }
}
