package gargoyle.ct.task.impl;

import gargoyle.ct.helper.TimeHelper;
import gargoyle.ct.task.CTTaskUpdatable;

import java.util.Arrays;
import java.util.TimerTask;

public class CTTimerTask extends TimerTask {

    private final CTTask task = new CTTask();

    private final TimeHelper timeHelper;

    private final Iterable<CTTaskUpdatable> updatables;

    public CTTimerTask(TimeHelper cTTimeHelper, CTTaskUpdatable... updatables) {
        this(cTTimeHelper, Arrays.asList(updatables));
    }

    public CTTimerTask(TimeHelper cTTimeHelper, Iterable<CTTaskUpdatable> updatables) {
        this.updatables = updatables;
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
