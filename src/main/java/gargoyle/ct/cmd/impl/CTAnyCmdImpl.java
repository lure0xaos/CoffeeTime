package gargoyle.ct.cmd.impl;

import gargoyle.ct.cmd.CTAnyCmd;
import gargoyle.ct.cmd.args.CTArgs;
import gargoyle.ct.cmd.args.impl.CTArgsImpl;

class CTAnyCmdImpl implements CTAnyCmd {
    protected final CTArgs args;

    CTAnyCmdImpl(String[] args) {
        this.args = new CTArgsImpl(args);
    }

    @Override
    public <T> T get(Class<T> type, String key) {
        return args.get(type, key);
    }

    @Override
    public <T> T get(String key, T def) {
        return args.get(key, def);
    }

    @Override
    public String get(String key, String def) {
        return args.getString(key, def);
    }

    @Override
    public boolean has(String key) {
        return args.hasArg(key);
    }

    public <T> T get(Class<T> type, String key, T def) {
        return args.get(type, key, def);
    }
}
