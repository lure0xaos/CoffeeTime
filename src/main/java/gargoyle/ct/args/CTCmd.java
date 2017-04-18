package gargoyle.ct.args;

import gargoyle.ct.log.Log;
import gargoyle.ct.util.CTTimeUtil;

public class CTCmd {

    private static final String MSG_FAKE_TIME_INVALID = "fake time not set";
    private final String[] args;

    public CTCmd(String[] args) {
        this.args = args.clone();
    }

    public long getFakeTime() {
        try {
            return args == null || args.length == 0 ? 0 : CTTimeUtil.parseHHMMSS(args[0]);
        } catch (NumberFormatException ex) {
            Log.info(MSG_FAKE_TIME_INVALID);
            return 0;
        }
    }

    public boolean isDebug() {
        return args != null && args.length == 1 || args != null && args.length == 2 && Boolean.parseBoolean(args[1]);
    }
}
