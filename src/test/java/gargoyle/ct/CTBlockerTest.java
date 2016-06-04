package gargoyle.ct;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CTBlockerTest implements MessageProvider {
	public static void main(final String[] args) throws Exception {
		final CTBlockerTest test = new CTBlockerTest();
		test.setUp();
		test.testBlocker();
	}

	private CTBlocker blocker;
	private ResourceBundle messages;

	@Override
	public String getMessage(final String message, final Object... args) {
		return MessageFormat.format(this.messages.getString(message), args);
	}

	@Before
	public void setUp() throws Exception {
		this.messages = ResourceBundle.getBundle("messages");
		this.blocker = new CTBlocker(this);
	}

	@After
	public void tearDown() throws Exception {
		this.blocker = null;
	}

	@Test
	public void testBlocker() {
		this.blocker.debug(true);
		this.blocker.setText("00:00");
		this.blocker.setVisible(true);
		this.blocker.toFront();
	}
}
