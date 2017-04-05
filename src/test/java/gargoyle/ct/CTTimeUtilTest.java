package gargoyle.ct;

import gargoyle.ct.util.CTTimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CTTimeUtilTest {
    private static final String BASE_2_FAILED_0_1 = "Base(2) failed:{0} {1}";
    private static final String BASE_3_FAILED_0_1 = "Base(3) failed:{0} {1}";
    private static final String BASE_FAILED_0_1 = "Base failed:{0} {1}";
    private static final String DOWN_TO_FAILED_0_1 = "downTo failed: {0} {1}";
    private static final String UP_TO_FAILED_0_1 = "upTo failed: {0} {1}";

    @Test
    public void testBetweenEager() {
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long eager = start - 100;
        Assert.assertFalse(CTTimeUtil.isBetween(eager, start, end));
    }

    @Test
    public void testBetweenEagerSec() {
        TimeUnit unit = TimeUnit.SECONDS;
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long eager = start - CTTimeUtil.toMillis(unit, 1);
        Assert.assertFalse(
                CTTimeUtil.isBetween(unit, CTTimeUtil.fromMillis(unit, eager), CTTimeUtil.fromMillis(unit, start),
                        CTTimeUtil.fromMillis(unit, end)));
    }

    @Test
    public void testBetweenLate() {
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long late = end + 100;
        Assert.assertFalse(CTTimeUtil.isBetween(late, start, end));
    }

    @Test
    public void testBetweenLateSec() {
        TimeUnit unit = TimeUnit.SECONDS;
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long late = end + CTTimeUtil.toMillis(unit, 1);
        Assert.assertFalse(
                CTTimeUtil.isBetween(unit, CTTimeUtil.fromMillis(unit, late), CTTimeUtil.fromMillis(unit, start),
                        CTTimeUtil.fromMillis(unit, end)));
    }

    @Test
    public void testBetweenTrue() {
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        Assert.assertTrue(CTTimeUtil.isBetween(current, start, end));
    }

    @Test
    public void testBetweenTrueSec() {
        TimeUnit unit = TimeUnit.SECONDS;
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        Assert.assertTrue(
                CTTimeUtil.isBetween(unit, CTTimeUtil.fromMillis(unit, current), CTTimeUtil.fromMillis(unit, start),
                        CTTimeUtil.fromMillis(unit, end)));
    }

    @Test
    public void testConvert() {
        long actual = CTTimeUtil.convert(TimeUnit.SECONDS, TimeUnit.MINUTES, 1);
        Assert.assertEquals(60, actual);
    }

    @Test
    public void testDownTo() {
        long currentMillis = CTTimeUtil.currentTimeMillis();
        long baseMillis = CTTimeUtil.toMillis(TimeUnit.HOURS, 1);
        long actual = CTTimeUtil.downTo(currentMillis, baseMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentMillis);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        long expected = calendar.getTimeInMillis();
        Assert.assertEquals(MessageFormat.format(DOWN_TO_FAILED_0_1, CTTimeUtil.formatHHMMSS(expected),
                CTTimeUtil.formatHHMMSS(actual)),
                expected, actual);
    }

    @Test
    public void testFormat() {
        long currentMillis = CTTimeUtil.make(10, 5, 30);
        Assert.assertEquals("10:05:30", CTTimeUtil.formatHHMMSS(currentMillis));
        Assert.assertEquals("05:30", CTTimeUtil.formatMMSS(currentMillis));
        Assert.assertEquals("05", CTTimeUtil.formatMM(currentMillis));
        Assert.assertEquals("30", CTTimeUtil.formatSS(currentMillis));
    }

    @Test
    public void testFromMillis() {
        Assert.assertEquals(1, CTTimeUtil.fromMillis(TimeUnit.SECONDS, 1000));
    }

    @Test
    public void testInPeriodFalse() {
        TimeUnit unit = TimeUnit.MINUTES;
        long currentMillis = CTTimeUtil.toMillis(unit, 10);
        int period = 60;
        int delay = 3;
        Assert.assertFalse(CTTimeUtil.isInPeriod(unit, currentMillis, period, delay));
    }

    @Test
    public void testInPeriodTrue() {
        TimeUnit unit = TimeUnit.MINUTES;
        long currentMillis = CTTimeUtil.toMillis(unit, 2);
        int period = 60;
        int delay = 3;
        Assert.assertTrue(CTTimeUtil.isInPeriod(unit, currentMillis, period, delay));
    }

    @Test
    public void testMake() {
        int hour = 14;
        int min = 20;
        int sec = 10;
        long current = CTTimeUtil.make(hour, min, sec);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(current);
        Assert.assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(min, calendar.get(Calendar.MINUTE));
        Assert.assertEquals(sec, calendar.get(Calendar.SECOND));
        Assert.assertEquals(0, calendar.get(Calendar.MILLISECOND));
    }

    @Test
    public void testParse() {
        String string = "11:12:13";
        long started = CTTimeUtil.parseHHMMSS(string);
        String formatted = CTTimeUtil.formatHHMMSS(started);
        Assert.assertEquals(string, formatted);
    }

    @Test
    public void testTimeElapsedFrom() {
        long current = CTTimeUtil.make(10, 10, 10);
        long begin = CTTimeUtil.make(10, 0, 0);
        Assert.assertEquals(10, CTTimeUtil.timeElapsedFrom(TimeUnit.MINUTES, current, begin));
    }

    @Test
    public void testTimeRemainsTo() {
        long current = CTTimeUtil.make(10, 49, 50);
        long end = CTTimeUtil.make(11, 0, 0);
        Assert.assertEquals(10, CTTimeUtil.timeRemainsTo(TimeUnit.MINUTES, current, end));
    }

    @Test
    public void testToBase() {
        // final long current = CTUtil.make(14, 20, 10);
        long current = CTTimeUtil.make();
        long baseMillis = CTTimeUtil.toMillis(TimeUnit.HOURS, 1);
        long expected = CTTimeUtil.downTo(current, baseMillis);
        long base = CTTimeUtil.toBase(expected, current, baseMillis);
        long base2 = CTTimeUtil.toBase(expected - baseMillis * 2, current, baseMillis);
        long base3 = CTTimeUtil.toBase(expected + baseMillis * 2, current, baseMillis);
        Assert.assertEquals(
                MessageFormat.format(BASE_FAILED_0_1, CTTimeUtil.formatHHMMSS(current), CTTimeUtil.formatHHMMSS(base)),
                expected,
                base);
        Assert.assertEquals(
                MessageFormat.format(BASE_2_FAILED_0_1, CTTimeUtil.formatHHMMSS(current), CTTimeUtil.formatHHMMSS(base2)),
                expected, base2);
        Assert.assertEquals(
                MessageFormat.format(BASE_3_FAILED_0_1, CTTimeUtil.formatHHMMSS(current), CTTimeUtil.formatHHMMSS(base3)),
                expected, base3);
    }

    @Test
    public void testToMillis() {
        Assert.assertEquals(1000, CTTimeUtil.toMillis(TimeUnit.SECONDS, 1));
    }

    @Test
    public void testUpTo() {
        long currentMillis = CTTimeUtil.currentTimeMillis();
        long baseMillis = CTTimeUtil.toMillis(TimeUnit.HOURS, 1);
        long actual = CTTimeUtil.upTo(currentMillis, baseMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentMillis);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.HOUR, 1);
        long expected = calendar.getTimeInMillis();
        Assert.assertEquals(
                MessageFormat.format(UP_TO_FAILED_0_1, CTTimeUtil.formatHHMMSS(expected), CTTimeUtil.formatHHMMSS(actual)),
                expected, actual);
    }
}
