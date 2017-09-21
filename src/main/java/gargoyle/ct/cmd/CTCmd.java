package gargoyle.ct.cmd;

public interface CTCmd extends CTAnyCmd {
    <T> T get(Class<T> type, String key, T def);

    long getFakeTime();

    boolean isDebug();
}
