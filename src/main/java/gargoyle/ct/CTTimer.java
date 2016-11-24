package gargoyle.ct;

import java.util.Timer;

public class CTTimer {

    private final Timer timer;

    private final CTTimerTask timerTask;

    public CTTimer(TimeHelper timeHelper, CTTaskUpdatable... updatables) {
        timer = new Timer(CTTimer.class.getName(), true);
        timerTask = new CTTimerTask(timeHelper, updatables);
        timer.scheduleAtFixedRate(timerTask, 100, 500);
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
