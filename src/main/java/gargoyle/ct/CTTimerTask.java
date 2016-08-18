package gargoyle.ct;

import java.util.TimerTask;

public class CTTimerTask extends TimerTask {
    private final CTTask task = new CTTask();
    private final TimeHelper timeHelper;
    private final CTTaskUpdatable[] updatables;

    public CTTimerTask(final TimeHelper cTTimeHelper, final CTTaskUpdatable... updatables) {
        this.updatables = updatables;
        this.timeHelper = cTTimeHelper;
    }

    public CTTask getTask() {
        return this.task;
    }

    @Override
    public void run() {
        for (final CTTaskUpdatable updatable : this.updatables) {
            updatable.doUpdate(this.task, this.timeHelper.currentTimeMillis());
        }
    }
}
