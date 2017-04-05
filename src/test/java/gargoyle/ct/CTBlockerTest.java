package gargoyle.ct;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.ui.CTBlocker;

import java.util.List;

public class CTBlockerTest implements MessageProvider {
    private static final String LOC_MESSAGES = "messages";
    private List<CTBlocker> blockers;
    private CTMessages messages;

    public static void main(String[] args) {
        CTBlockerTest test = new CTBlockerTest();
        test.messages = new CTMessages(LOC_MESSAGES);
        test.blockers = CTBlocker.forAllDevices();
        for (CTBlocker blocker : test.blockers) {
            blocker.debug(true);
            blocker.setText("00:00");
            blocker.setVisible(true);
            blocker.toFront();
        }
    }

    @Override
    public String getMessage(String message, Object... args) {
        return messages == null ? null : messages.getMessage(message, args);
    }
}
