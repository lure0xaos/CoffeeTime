package gargoyle.ct.ui;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.util.CTTimeUtil;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class CTBlockerTextProvider {

    private static final int    DELAY             = 3;
    private static final String LOC_MESSAGES      = "messages.blocker";
    private static final int    PERIOD            = 60;
    private static final String STR_BLOCKED_BIG   = "blocked_w-big";
    private static final String STR_BLOCKED_SMALL = "blocked_w-small";
    private static final String STR_WARN_BIG      = "warn_w-big";
    private static final String STR_WARN_SMALL    = "warn_w-small";
    private final MessageProvider messages;

    public CTBlockerTextProvider(CTPreferences preferences) {
        CTMessages messages = new CTMessages(LOC_MESSAGES);
        preferences.supportedLocales().bind(messages.locale());
        this.messages = messages;
    }

    public String getBlockerText(CTTask task, long currentMillis, boolean big) {
        if (task.isReady()) {
            if (task.isBlocked(currentMillis)) {
                return messages.getMessage(big ? STR_BLOCKED_BIG : STR_BLOCKED_SMALL,
                                           CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis,
                                                                                          task.getBlockEnd
                                                                                                  (currentMillis))));
            }
            if (task.isWarn(currentMillis)) {
                return CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, PERIOD, DELAY) ?
                       messages.getMessage(big ? STR_WARN_BIG : STR_WARN_SMALL,
                                           CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis,
                                                                                          task.getBlockStart(
                                                                                                  currentMillis)))) :
                       null;
            }
        }
        return null;
    }

    public Color getColor(CTTask task, long currentMillis) {
        if (task.isReady()) {
            if (task.isBlocked(currentMillis)) {
                return Color.WHITE;
            }
            if (task.isWarn(currentMillis)) {
                return CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, PERIOD, DELAY) ? Color.GREEN : null;
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    public String getInfoText(CTTask task, long currentMillis) {
        return CTTimeUtil.formatHHMMSS(currentMillis);
    }

    public String getToolTipText(CTTask task, long currentMillis) {
        String toolTipText = CTTimeUtil.formatHHMMSS(currentMillis);
        if (task.isReady()) {
            if (task.isBlocked(currentMillis)) {
                toolTipText = CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis,
                                                                             task.getBlockEnd(currentMillis)));
            }
            if (task.isWarn(currentMillis)) {
                toolTipText = CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis,
                                                                             task.getBlockStart(currentMillis)));
            }
            if (task.isSleeping(currentMillis)) {
                toolTipText = CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis,
                                                                             task.getBlockStart(currentMillis)));
            }
        }
        return toolTipText;
    }

    public boolean isVisible(CTTask task, long currentMillis) {
        if (task.isReady()) {
            if (task.isBlocked(currentMillis)) {
                return true;
            }
            if (task.isWarn(currentMillis)) {
                return CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, PERIOD, DELAY);
            }
        }
        return false;
    }
}
