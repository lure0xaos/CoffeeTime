package gargoyle.ct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CTBlockerTest {
	public static void main(final String[] args) throws Exception {
		final CTBlockerTest test = new CTBlockerTest();
		test.setUp();
		test.testBlocker();
	}

	private CTBlocker blocker;

	@Before
	public void setUp() throws Exception {
		this.blocker = new CTBlocker();
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
