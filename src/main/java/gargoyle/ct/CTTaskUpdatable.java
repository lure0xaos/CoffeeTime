package gargoyle.ct;

@FunctionalInterface
public interface CTTaskUpdatable {

    void doUpdate(CTTask task, long currentMillis);
}
