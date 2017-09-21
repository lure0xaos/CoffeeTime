package gargoyle.ct;

import gargoyle.ct.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("HardCodedStringLiteral")
public class CTLogTest {
    private final Logger logger = Logger.getLogger(CTLogTest.class.getName());
    private Deque<LogRecord> records;

    @BeforeEach
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
        Log.warn("test");
        assertEquals(CTLogTest.class.getName(), records.getLast().getSourceClassName(), "wrong class logged");
    }
}
