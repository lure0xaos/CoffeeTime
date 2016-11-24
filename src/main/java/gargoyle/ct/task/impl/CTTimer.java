package gargoyle.ct.task.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.helper.TimeHelper;
import gargoyle.ct.task.CTTaskUpdatable;

import java.util.Timer;

public class CTTimer {

    private static final int CHECK_DELAY = 100;

    private static final int CHECK_PERIOD = 500;

    private final Timer timer;

    private final CTTimerTask timerTask;

    public CTTimer(TimeHelper timeHelper, CTTaskUpdatable... updatables) {
        timer = new Timer(CTTimer.class.getName(), true);
        timerTask = new CTTimerTask(timeHelper, updatables);
        timer.scheduleAtFixedRate(timerTask, CHECK_DELAY, CHECK_PERIOD);
    }

    public void arm(CTConfig config, long currentMillis) {
        CTTask task = timerTask.getTask();
        task.setConfig(config);
        task.setStarted(currentMillis);
    }

    public void terminate() {
        timer.cancel();
    }

    public void unarm() {
        CTTask task = timerTask.getTask();
        task.setStarted(0);
    }
}
