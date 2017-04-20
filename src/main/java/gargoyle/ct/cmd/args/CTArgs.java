package gargoyle.ct.cmd.args;

public interface CTArgs {

    <T> T get(Class<T> type, String key);

    <T> T get(Class<T> type, String key, T def);

    <T> T get(int index, T def);

    <T> T get(String key, T def);

    boolean getBoolean(int index);

    boolean getBoolean(String key);

    boolean getBoolean(int index, boolean def);

    boolean getBoolean(String key, boolean def);

    byte getByte(int index);

    byte getByte(String key);

    byte getByte(int index, byte def);

    byte getByte(String key, byte def);

    byte[] getBytes(int index);

    byte[] getBytes(String key);

    byte[] getBytes(int index, byte[] def);

    byte[] getBytes(String key, byte[] def);

    char getChar(int index);

    char getChar(String key);

    char getChar(int index, char def);

    char getChar(String key, char def);

    double getDouble(int index);

    double getDouble(String key);

    double getDouble(int index, double def);

    double getDouble(String key, double def);

    float getFloat(int index);

    float getFloat(String key);

    float getFloat(int index, float def);

    float getFloat(String key, float def);

    int getInteger(int index);

    int getInteger(String key);

    int getInteger(int index, int def);

    int getInteger(String key, int def);

    long getLong(int index);

    long getLong(String key);

    long getLong(int index, long def);

    long getLong(String key, long def);

    short getShort(int index);

    short getShort(String key);

    short getShort(int index, short def);

    short getShort(String key, short def);

    String getString(int index);

    String getString(String key);

    String getString(int index, String def);

    String getString(String key, String def);

    boolean hasArg(int index);

    boolean hasArg(String key);

    int size();
}
