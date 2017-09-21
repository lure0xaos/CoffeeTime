package gargoyle.ct.task;

import gargoyle.ct.task.impl.CTTask;

@FunctionalInterface
public interface CTTaskUpdatable {
    void doUpdate(CTTask task, long currentMillis);
}
