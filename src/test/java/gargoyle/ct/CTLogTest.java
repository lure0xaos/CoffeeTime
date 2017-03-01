package gargoyle.ct;

import gargoyle.ct.util.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@SuppressWarnings("HardCodedStringLiteral")
public class CTLogTest {

    private final Logger logger = Logger.getLogger(CTLogTest.class.getName());

    private Deque<LogRecord> records;

    @Before
    public void setUp() {
        records = new LinkedList<>();
        logger.addHandler(new Handler() {
            @Override
            public void close() throws SecurityException {
                //
            }

            @Override
            public void flush() {
                //
            }

            @Override
            public void publish(LogRecord record) {
                records.add(record);
            }
        });
    }

    @Test
    public void testLog() {
        Log.warn("test");
        Assert.assertEquals(CTLogTest.class.getName(), records.getLast().getSourceClassName());
    }
}
