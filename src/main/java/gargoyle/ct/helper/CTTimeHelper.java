package gargoyle.ct.helper;

public interface CTTimeHelper {
    long currentTimeMillis();

    long getFakeTime();

    void setFakeTime(long fakeTime);
}
