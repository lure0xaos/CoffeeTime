package gargoyle.ct.cmd.impl;

import gargoyle.ct.cmd.CTCmd;
import gargoyle.ct.cmd.args.CTArgs;
import gargoyle.ct.cmd.args.impl.CTArgsImpl;
import gargoyle.ct.log.Log;
import gargoyle.ct.util.CTTimeUtil;

public class CTCmdImpl implements CTCmd {

    private static final String MSG_FAKE_TIME_INVALID = "fake time not set";
    private final CTArgs args;

    public CTCmdImpl(String[] args) {
        this.args = new CTArgsImpl(args);
    }

    @Override
    public <T> T get(Class<T> type, String key) {return args.get(type, key);}

    @Override
    public <T> T get(String key, T def) {return args.get(key, def);}

    @Override
    public String get(String key, String def) {return args.getString(key, def);}

    @Override
    public boolean has(String key) {return args.hasArg(key);}

    @Override
    public long getFakeTime() {
        try {
            return args.hasArg(0) ? CTTimeUtil.parseHHMMSS(args.getString(0)) : 0;
        } catch (NumberFormatException ex) {
            Log.info(MSG_FAKE_TIME_INVALID);
            return 0;
        }
    }

    @Override
    public boolean isDebug() {
        return args.size() == 1 || args.size() > 1 && args.getBoolean(1);
    }
}
