package gargoyle.ct;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CTTimeUtilTest {
	@Before
	public void setUp() throws Exception {
		CTTimeUtil.class.getName();
	}

	@After
	public void tearDown() throws Exception {
		CTTimeUtil.class.getName();
	}

	@Test
	public void testBetweenEager() {
		final long current = CTTimeUtil.make();
		final long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long eager = start - 100;
		Assert.assertFalse(CTTimeUtil.isBetween(eager, start, end));
	}

	@Test
	public void testBetweenEagerSec() {
		final TimeUnit unit = TimeUnit.SECONDS;
		final long current = CTTimeUtil.make();
		final long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long eager = start - CTTimeUtil.toMillis(unit, 1);
		Assert.assertFalse(CTTimeUtil.isBetween(unit, CTTimeUtil.fromMillis(unit, eager), CTTimeUtil.fromMillis(unit, start),
				CTTimeUtil.fromMillis(unit, end)));
	}

	@Test
	public void testBetweenLate() {
		final long current = CTTimeUtil.make();
		final long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long late = end + 100;
		Assert.assertFalse(CTTimeUtil.isBetween(late, start, end));
	}

	@Test
	public void testBetweenLateSec() {
		final TimeUnit unit = TimeUnit.SECONDS;
		final long current = CTTimeUtil.make();
		final long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long late = end + CTTimeUtil.toMillis(unit, 1);
		Assert.assertFalse(CTTimeUtil.isBetween(unit, CTTimeUtil.fromMillis(unit, late), CTTimeUtil.fromMillis(unit, start),
				CTTimeUtil.fromMillis(unit, end)));
	}

	@Test
	public void testBetweenTrue() {
		final long current = CTTimeUtil.make();
		final long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		Assert.assertTrue(CTTimeUtil.isBetween(current, start, end));
	}

	@Test
	public void testBetweenTrueSec() {
		final TimeUnit unit = TimeUnit.SECONDS;
		final long current = CTTimeUtil.make();
		final long start = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTTimeUtil.upTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		Assert.assertTrue(CTTimeUtil.isBetween(unit, CTTimeUtil.fromMillis(unit, current), CTTimeUtil.fromMillis(unit, start),
				CTTimeUtil.fromMillis(unit, end)));
	}

	@Test
	public void testConvert() {
		final long actual = CTTimeUtil.convert(TimeUnit.SECONDS, TimeUnit.MINUTES, 1);
		Assert.assertEquals(60, actual);
	}

	@Test
	public void testDownTo() {
		final long currentMillis = CTTimeUtil.currentTimeMillis();
		final long baseMillis = CTTimeUtil.toMillis(TimeUnit.HOURS, 1);
		final long actual = CTTimeUtil.downTo(currentMillis, baseMillis);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currentMillis);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		final long expected = calendar.getTimeInMillis();
		Assert.assertEquals("downTo failed: " + CTTimeUtil.formatHHMMSS(expected) + " " + CTTimeUtil.formatHHMMSS(actual),
				expected, actual);
	}

	@Test
	public void testFormat() {
		final long currentMillis = CTTimeUtil.make(10, 5, 30);
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
		final TimeUnit unit = TimeUnit.MINUTES;
		final long currentMillis = CTTimeUtil.toMillis(unit, 10);
		final int period = 60;
		final int delay = 3;
		Assert.assertFalse(CTTimeUtil.isInPeriod(unit, currentMillis, period, delay));
	}

	@Test
	public void testInPeriodTrue() {
		final TimeUnit unit = TimeUnit.MINUTES;
		final long currentMillis = CTTimeUtil.toMillis(unit, 2);
		final int period = 60;
		final int delay = 3;
		Assert.assertTrue(CTTimeUtil.isInPeriod(unit, currentMillis, period, delay));
	}

	@Test
	public void testMake() {
		final int hour = 14;
		final int min = 20;
		final int sec = 10;
		final long current = CTTimeUtil.make(hour, min, sec);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(current);
		Assert.assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(min, calendar.get(Calendar.MINUTE));
		Assert.assertEquals(sec, calendar.get(Calendar.SECOND));
		Assert.assertEquals(0, calendar.get(Calendar.MILLISECOND));
	}

	@Test
	public void testParse() {
		final String string = "11:12:13";
		final long started = CTTimeUtil.parseHHMMSS(string);
		final String formatted = CTTimeUtil.formatHHMMSS(started);
		Assert.assertEquals(string, formatted);
	}

	@Test
	public void testTimeElapsedFrom() {
		final long current = CTTimeUtil.make(10, 10, 10);
		final long begin = CTTimeUtil.make(10, 0, 0);
		Assert.assertEquals(10, CTTimeUtil.timeElapsedFrom(TimeUnit.MINUTES, current, begin));
	}

	@Test
	public void testTimeRemainsTo() {
		final long current = CTTimeUtil.make(10, 49, 50);
		final long end = CTTimeUtil.make(11, 0, 0);
		Assert.assertEquals(10, CTTimeUtil.timeRemainsTo(TimeUnit.MINUTES, current, end));
	}

	@Test
	public void testToBase() {
		// final long current = CTUtil.make(14, 20, 10);
		final long current = CTTimeUtil.make();
		final long baseMillis = CTTimeUtil.toMillis(TimeUnit.HOURS, 1);
		final long expected = CTTimeUtil.downTo(current, baseMillis);
		final long base = CTTimeUtil.toBase(expected, current, baseMillis);
		final long base2 = CTTimeUtil.toBase(expected - (baseMillis * 2), current, baseMillis);
		final long base3 = CTTimeUtil.toBase(expected + (baseMillis * 2), current, baseMillis);
		Assert.assertEquals("Base failed:" + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(base), expected,
				base);
		Assert.assertEquals("Base(2) failed:" + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(base2),
				expected, base2);
		Assert.assertEquals("Base(3) failed:" + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(base3),
				expected, base3);
	}

	@Test
	public void testToMillis() {
		Assert.assertEquals(1000, CTTimeUtil.toMillis(TimeUnit.SECONDS, 1));
	}

	@Test
	public void testUpTo() {
		final long currentMillis = CTTimeUtil.currentTimeMillis();
		final long baseMillis = CTTimeUtil.toMillis(TimeUnit.HOURS, 1);
		final long actual = CTTimeUtil.upTo(currentMillis, baseMillis);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currentMillis);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.add(Calendar.HOUR, 1);
		final long expected = calendar.getTimeInMillis();
		Assert.assertEquals("upTo failed: " + CTTimeUtil.formatHHMMSS(expected) + " " + CTTimeUtil.formatHHMMSS(actual),
				expected, actual);
	}
}
