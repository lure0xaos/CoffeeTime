package gargoyle.ct;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class CTBlockerTest implements MessageProvider {
	public static void main(final String[] args) throws Exception {
		final CTBlockerTest test = new CTBlockerTest();
		test.messages = ResourceBundle.getBundle("messages");
		test.blocker = new CTBlocker(test);
		test.blocker.debug(true);
		test.blocker.setText("00:00");
		test.blocker.setVisible(true);
		test.blocker.toFront();
	}

	private CTBlocker blocker;
	private ResourceBundle messages;

	@Override
	public String getMessage(final String message, final Object... args) {
		assert this.messages != null;
		return MessageFormat.format(this.messages.getString(message), args);
	}
}
