package gargoyle.ct.task.helper;

public interface CTTimeHelper {
    long currentTimeMillis();

    long getFakeTime();

    void setFakeTime(long fakeTime);
}
