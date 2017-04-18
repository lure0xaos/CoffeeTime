package gargoyle.ct.cmd.args.impl;

import gargoyle.ct.cmd.args.CTArgs;

public class CTArgsImpl implements CTArgs {

    private final String[] args;

    public CTArgsImpl(String[] args) { this.args = args == null ? new String[0] : args.clone();}

    @Override
    public String getString(int index) {
        return args.length > index ? args[index] : "";
    }

    @Override
    public boolean getBoolean(int index) {
        return args.length > index && Boolean.parseBoolean(args[index]);
    }

    @Override
    public int size() {
        return args.length;
    }

    @Override
    public boolean hasArg(int index) {
        return args.length > index;
    }
}
