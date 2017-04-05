package gargoyle.ct.helper;

public interface TimeHelper {
    long currentTimeMillis();

    long getFakeTime();

    void setFakeTime(long fakeTime);
}
