package gargoyle.ct.cmd.args;

public interface CTArgs {

    String getString(int index);

    boolean getBoolean(int index);

    int size();

    boolean hasArg(int index);
}
