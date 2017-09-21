package gargoyle.ct;

import gargoyle.ct.util.CTTimeUtil;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("MagicNumber")
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
        assertFalse(CTTimeUtil.isBetween(eager, start, end), "eager interval check failed");
    }

    @Test
    public void testBetweenEagerSec() {
        TimeUnit unit = TimeUnit.SECONDS;
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long eager = start - CTTimeUtil.toMillis(unit, 1);
        assertFalse(
                CTTimeUtil.isBetween(unit,
                        CTTimeUtil.fromMillis(unit, eager),
                        CTTimeUtil.fromMillis(unit, start),
                        CTTimeUtil.fromMillis(unit, end)),
                "interval in sec check failed");
    }

    @Test
    public void testBetweenLate() {
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long late = end + 100;
        assertFalse(CTTimeUtil.isBetween(late, start, end), "late interval check failed");
    }

    @Test
    public void testBetweenLateSec() {
        TimeUnit unit = TimeUnit.SECONDS;
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long late = end + CTTimeUtil.toMillis(unit, 1);
        assertFalse(
                CTTimeUtil.isBetween(unit,
                        CTTimeUtil.fromMillis(unit, late),
                        CTTimeUtil.fromMillis(unit, start),
                        CTTimeUtil.fromMillis(unit, end)),
                "late interval in sec check failed");
    }

    @Test
    public void testBetweenTrue() {
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        assertTrue(CTTimeUtil.isBetween(current, start, end), "hour interval fail");
    }

    @Test
    public void testBetweenTrueSec() {
        TimeUnit unit = TimeUnit.SECONDS;
        long current = CTTimeUtil.make();
        long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        assertTrue(
                CTTimeUtil.isBetween(unit,
                        CTTimeUtil.fromMillis(unit, current),
                        CTTimeUtil.fromMillis(unit, start),
                        CTTimeUtil.fromMillis(unit, end)),
                "hour interval in sec fail");
    }

    @Test
    public void testConvert() {
        long actual = CTTimeUtil.convert(TimeUnit.SECONDS, TimeUnit.MINUTES, 1);
        assertEquals(60, actual, "time unit conversion failed");
    }

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
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
        assertEquals(expected, actual,
                MessageFormat.format(DOWN_TO_FAILED_0_1, CTTimeUtil.formatHHMMSS(expected),
                        CTTimeUtil.formatHHMMSS(actual)));//NON-NLS
    }

    @Test
    public void testFormat() {
        long currentMillis = CTTimeUtil.make(10, 5, 30);
        assertEquals("10:05:30", CTTimeUtil.formatHHMMSS(currentMillis), "format fail");
        assertEquals("05:30", CTTimeUtil.formatMMSS(currentMillis), "format fail");
        assertEquals("05", CTTimeUtil.formatMM(currentMillis), "format fail");
        assertEquals("30", CTTimeUtil.formatSS(currentMillis), "format fail");
    }

    @Test
    public void testFromMillis() {
        assertEquals(1, CTTimeUtil.fromMillis(TimeUnit.SECONDS, 1000), "something wrong with millis");
    }

    @Test
    public void testInPeriodFalse() {
        TimeUnit unit = TimeUnit.MINUTES;
        long currentMillis = CTTimeUtil.toMillis(unit, 10);
        int period = 60;
        int delay = 3;
        assertFalse(CTTimeUtil.isInPeriod(unit, currentMillis, period, delay), "should not be in period");
    }

    @Test
    public void testInPeriodTrue() {
        TimeUnit unit = TimeUnit.MINUTES;
        long currentMillis = CTTimeUtil.toMillis(unit, 2);
        int period = 60;
        int delay = 3;
        assertTrue(CTTimeUtil.isInPeriod(unit, currentMillis, period, delay), "should be in period");
    }

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
    @Test
    public void testMake() {
        int hour = 14;
        int min = 20;
        int sec = 10;
        long current = CTTimeUtil.make(hour, min, sec);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(current);
        assertAll(
                () -> assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY), "make fails"),
                () -> assertEquals(min, calendar.get(Calendar.MINUTE), "make fails"),
                () -> assertEquals(sec, calendar.get(Calendar.SECOND), "make fails"),
                () -> assertEquals(0, calendar.get(Calendar.MILLISECOND), "make fails")
        );
    }

    @Test
    public void testParse() {
        String string = "11:12:13";
        long started = CTTimeUtil.parseHHMMSS(string);
        String formatted = CTTimeUtil.formatHHMMSS(started);
        assertEquals(string, formatted, "parsing failed");
    }

    @Test
    public void testTimeElapsedFrom() {
        long current = CTTimeUtil.make(10, 10, 10);
        long begin = CTTimeUtil.make(10, 0, 0);
        assertEquals(10, CTTimeUtil.timeElapsedFrom(TimeUnit.MINUTES, current, begin), " elapsed time wrong");
    }

    @Test
    public void testTimeRemainsTo() {
        long current = CTTimeUtil.make(10, 49, 50);
        long end = CTTimeUtil.make(11, 0, 0);
        assertEquals(10, CTTimeUtil.timeRemainsTo(TimeUnit.MINUTES, current, end), "remaining time wrong");
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
        assertEquals(expected, base,
                MessageFormat.format(BASE_FAILED_0_1, CTTimeUtil.formatHHMMSS(current), CTTimeUtil.formatHHMMSS(base))); //NON-NLS
        assertEquals(expected, base2,
                MessageFormat.format(BASE_2_FAILED_0_1, CTTimeUtil.formatHHMMSS(current), CTTimeUtil.formatHHMMSS(base2)));//NON-NLS
        assertEquals(expected, base3,
                MessageFormat.format(BASE_3_FAILED_0_1, CTTimeUtil.formatHHMMSS(current), CTTimeUtil.formatHHMMSS(base3)));//NON-NLS
    }

    @Test
    public void testToMillis() {
        assertEquals(1000, CTTimeUtil.toMillis(TimeUnit.SECONDS, 1),
                "toMillis fail");
    }

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
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
        assertEquals(expected, actual,
                MessageFormat.format(UP_TO_FAILED_0_1, CTTimeUtil.formatHHMMSS(expected), CTTimeUtil.formatHHMMSS(actual))); //NON-NLS
    }
}
