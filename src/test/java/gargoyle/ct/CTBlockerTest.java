package gargoyle.ct;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessageProvider;
import gargoyle.ct.ui.CTBlocker;

public class CTBlockerTest implements MessageProvider {

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static void main(String[] args) {
        CTBlockerTest test = new CTBlockerTest();
        test.messages = new CTMessageProvider(CT.LOC_MESSAGES);
        test.blocker = new CTBlocker(test);
        test.blocker.debug(true);
        test.blocker.setText("00:00");
        test.blocker.setVisible(true);
        test.blocker.toFront();
    }

    private CTBlocker blocker;

    private CTMessageProvider messages;

    @Override
    public String getMessage(String message, Object... args) {
        return messages == null ? null : messages.getMessage(message, args);
    }
}
