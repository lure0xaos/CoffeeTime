package gargoyle.ct;

public class CTBlockerTest implements MessageProvider {
	public static void main(final String[] args) throws Exception {
		final CTBlockerTest test = new CTBlockerTest();
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
	public String getMessage(final String message, final Object... args) {
		return this.messages == null ? null : this.messages.getMessage(message, args);
	}
}
