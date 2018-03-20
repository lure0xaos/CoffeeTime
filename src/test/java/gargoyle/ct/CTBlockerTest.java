package gargoyle.ct;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.CTPreferencesImpl;
import gargoyle.ct.ui.impl.CTBlocker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.util.List;

public class CTBlockerTest implements MessageProvider {
    private List<CTBlocker> blockers;
    private CTMessages messages;

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static void main(String[] args) {
        CTBlockerTest test = new CTBlockerTest();
        test.messages = new CTMessages("messages");
        CTPreferences pref = new CTPreferencesImpl(CTBlockerTest.class);
        test.blockers = CTBlocker.forAllDevices(pref);
        for (CTBlocker blocker : test.blockers) {
            blocker.debug(true);
            blocker.showText(Color.WHITE, "00:00");
            blocker.setVisible(true);
            blocker.toFront();
        }
    }

    @Nullable
    @Override
    public String getMessage(@NotNull String message, Object... args) {
        return messages == null ? null : messages.getMessage(message, args);
    }
}
