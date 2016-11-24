package gargoyle.ct.task.impl;

import gargoyle.ct.helper.TimeHelper;
import gargoyle.ct.task.CTTaskUpdatable;

import java.util.TimerTask;

public class CTTimerTask extends TimerTask {

    private final CTTask task = new CTTask();

    private final TimeHelper timeHelper;

    private final CTTaskUpdatable[] updatables;

    public CTTimerTask(TimeHelper cTTimeHelper, CTTaskUpdatable... updatables) {
        this.updatables = updatables.clone();
        timeHelper = cTTimeHelper;
    }

    public CTTask getTask() {
        return task;
    }

    @Override
    public void run() {
        for (CTTaskUpdatable updatable : updatables) {
            updatable.doUpdate(task, timeHelper.currentTimeMillis());
        }
    }
}
