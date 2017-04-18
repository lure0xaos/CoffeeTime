package gargoyle.ct;

import gargoyle.ct.log.Log;
import org.junit.Assert;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CTLogTest {

    private static final Deque<LogRecord> records = new LinkedList<>();

    @Test
    public void testLog() {
        Log.warn("test"); //NON-NLS
        Assert.assertEquals(CTLogTest.class.getName(), records.getLast().getSourceClassName());
    }

    public static class ListHandler extends Handler {

        private final Deque<LogRecord> records;

        public ListHandler() {
            records = CTLogTest.records;
        }

        @Override
        public void publish(LogRecord record) {
            records.add(record);
        }

        @Override
        public void flush() {
            //
        }

        @Override
        public void close() {
            //
        }
    }
}
