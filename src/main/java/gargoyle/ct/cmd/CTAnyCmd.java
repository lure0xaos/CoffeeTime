package gargoyle.ct.cmd;

public interface CTAnyCmd {
    <T> T get(Class<T> type, String key);

    <T> T get(String key, T def);

    String get(String key, String def);

    boolean has(String key);
}
