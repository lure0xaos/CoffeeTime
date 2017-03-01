package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.util.CTTimeUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static gargoyle.ct.config.CTStandardConfigs.BLOCK_10M;
import static gargoyle.ct.config.CTStandardConfigs.WARN_3M;
import static gargoyle.ct.config.CTStandardConfigs.WHOLE_1H;

@SuppressWarnings("MagicNumber")
public class CTTaskTest {

    private static final String TEST_BLOCKED_FAILED_0_1 = "testBlocked failed: {0} {1}";

    private static final String TEST_SLEEPING_FAILED_0_1 = "testSleeping failed: {0} {1}";

    private static final String TEST_WARN_FAILED_0_1 = "testWarn failed: {0} {1}";

    private long actual;

    private CTTask task;

    @Before
    public void setUp() {
        task = new CTTask();
    }

    @After
    public void tearDown() {
        task = null;
    }

    @Test
    public void testBlockedFalse() {
        long current = CTTimeUtil.make(10, 45, 10);
        Assert.assertFalse(task.isBlocked(current));
        task.setConfig(new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M));
        long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
        Assert.assertFalse(MessageFormat.format(TEST_BLOCKED_FAILED_0_1, CTTimeUtil.formatHHMMSS(current),
            CTTimeUtil.formatHHMMSS(started)), //NON-NLS
            task.isBlocked(current));
    }

    @Test
    public void testBlockedTrue() {
        task.setConfig(new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M));
        long current = CTTimeUtil.make(10, 55, 10);
        long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
        Assert.assertTrue(MessageFormat.format(TEST_BLOCKED_FAILED_0_1, CTTimeUtil.formatHHMMSS(current),
            CTTimeUtil.formatHHMMSS(started)), //NON-NLS
            task.isBlocked(current));
    }

    @Test
    public void testSleepingFalse() {
        long current = CTTimeUtil.make(10, 51, 10);
        Assert.assertFalse(task.isWarn(current));
        task.setConfig(new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M));
        long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
        Assert.assertFalse(MessageFormat.format(TEST_SLEEPING_FAILED_0_1, CTTimeUtil.formatHHMMSS(current),
            CTTimeUtil.formatHHMMSS(started)), //NON-NLS
            task.isSleeping(current));
    }

    @Test
    public void testSleepingTrue() {
        task.setConfig(new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M));
        long current = CTTimeUtil.make(10, 30, 10);
        long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
        Assert.assertTrue(MessageFormat.format(TEST_SLEEPING_FAILED_0_1, CTTimeUtil.formatHHMMSS(current),
            CTTimeUtil.formatHHMMSS(started)), //NON-NLS
            task.isSleeping(current));
    }

    @Test
    public void testStarted() {
        long started = CTTimeUtil.make();
        task.setStarted(started);
        actual = task.getStarted();
        Assert.assertEquals(started, actual);
    }

    @Test
    public void testStartedUnits() {
        long started = CTTimeUtil.make();
        TimeUnit unit = TimeUnit.SECONDS;
        task.setStarted(unit, started);
        actual = task.getStarted(unit);
        Assert.assertEquals(started, actual);
    }

    @Test
    public void testWarnFalse() {
        task.setConfig(new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M));
        long current = CTTimeUtil.make(10, 45, 10);
        long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
        Assert.assertFalse(MessageFormat.format(TEST_WARN_FAILED_0_1, CTTimeUtil.formatHHMMSS(current),
            CTTimeUtil.formatHHMMSS(started)), //NON-NLS
            task.isWarn(current));
    }

    @Test
    public void testWarnTrue() {
        task.setConfig(new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M));
        long current = CTTimeUtil.make(10, 49, 10);
        long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
        Assert.assertTrue(MessageFormat.format(TEST_WARN_FAILED_0_1, CTTimeUtil.formatHHMMSS(current),
            CTTimeUtil.formatHHMMSS(started)), //NON-NLS
            task.isWarn(current));
    }
}
