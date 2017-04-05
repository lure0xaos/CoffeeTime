package gargoyle.ct.task.impl;

import gargoyle.ct.helper.CTTimeHelper;
import gargoyle.ct.task.CTTaskUpdatable;

import java.util.Arrays;
import java.util.TimerTask;

public class CTTimerTask extends TimerTask {
    private final CTTask task = new CTTask();
    private final CTTimeHelper timeHelper;
    private final Iterable<CTTaskUpdatable> updatables;

    public CTTimerTask(CTTimeHelper timeHelper, CTTaskUpdatable... updatables) {
        this(timeHelper, Arrays.asList(updatables));
    }

    public CTTimerTask(CTTimeHelper timeHelper, Iterable<CTTaskUpdatable> updatables) {
        this.updatables = updatables;
        this.timeHelper = timeHelper;
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
