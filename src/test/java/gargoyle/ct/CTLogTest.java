package gargoyle.ct;

import gargoyle.ct.helper.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class CTLogTest {
    private final Logger logger = Logger.getLogger(CTLogTest.class.getName());
    private Deque<LogRecord> records;

    @Before
    public void setUp() {
        records = new LinkedList<>();
        logger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                records.add(record);
            }

            @Override
            public void flush() {
                //
            }

            @Override
            public void close() throws SecurityException {
                //
            }
        });
    }

    @Test
    public void testLog() {
        Log.warn("test"); //NON-NLS
        Assert.assertEquals(CTLogTest.class.getName(), records.getLast().getSourceClassName());
    }
}
