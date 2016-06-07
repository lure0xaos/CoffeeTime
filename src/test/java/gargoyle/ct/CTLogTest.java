package gargoyle.ct;

import java.util.LinkedList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CTLogTest {
	LinkedList<LogRecord> records;

	@Before
	public void setUp() {
		this.records = new LinkedList<LogRecord>();
		Logger.getLogger(CTLogTest.class.getName()).addHandler(new Handler() {
			@Override
			public void close() throws SecurityException {
			}

			@Override
			public void flush() {
			}

			@Override
			public void publish(final LogRecord record) {
				CTLogTest.this.records.add(record);
			}
		});
	}

	@Test
	public void testLog() {
		Log.warn("test");
		Assert.assertEquals(CTLogTest.class.getName(), this.records.getLast().getSourceClassName());
	}
}
