package gargoyle.ct;

public interface TimeHelper {
    long currentTimeMillis();

    long getFakeTime();

    void setFakeTime(long fakeTime);
}
