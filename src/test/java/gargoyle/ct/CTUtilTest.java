package gargoyle.ct;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CTUtilTest {
	@Before
	public void setUp() throws Exception {
		CTUtil.class.getName();
	}

	@After
	public void tearDown() throws Exception {
		CTUtil.class.getName();
	}

	@Test
	public void testBetweenEager() {
		final long current = CTUtil.make();
		final long start = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTUtil.upTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long eager = start - 100;
		Assert.assertFalse(CTUtil.isBetween(eager, start, end));
	}

	@Test
	public void testBetweenEagerSec() {
		final TimeUnit unit = TimeUnit.SECONDS;
		final long current = CTUtil.make();
		final long start = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTUtil.upTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long eager = start - CTUtil.toMillis(unit, 1);
		Assert.assertFalse(CTUtil.isBetween(unit, CTUtil.fromMillis(unit, eager), CTUtil.fromMillis(unit, start),
				CTUtil.fromMillis(unit, end)));
	}

	@Test
	public void testBetweenLate() {
		final long current = CTUtil.make();
		final long start = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTUtil.upTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long late = end + 100;
		Assert.assertFalse(CTUtil.isBetween(late, start, end));
	}

	@Test
	public void testBetweenLateSec() {
		final TimeUnit unit = TimeUnit.SECONDS;
		final long current = CTUtil.make();
		final long start = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTUtil.upTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long late = end + CTUtil.toMillis(unit, 1);
		Assert.assertFalse(CTUtil.isBetween(unit, CTUtil.fromMillis(unit, late), CTUtil.fromMillis(unit, start),
				CTUtil.fromMillis(unit, end)));
	}

	@Test
	public void testBetweenTrue() {
		final long current = CTUtil.make();
		final long start = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTUtil.upTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		Assert.assertTrue(CTUtil.isBetween(current, start, end));
	}

	@Test
	public void testBetweenTrueSec() {
		final TimeUnit unit = TimeUnit.SECONDS;
		final long current = CTUtil.make();
		final long start = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		final long end = CTUtil.upTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		Assert.assertTrue(CTUtil.isBetween(unit, CTUtil.fromMillis(unit, current), CTUtil.fromMillis(unit, start),
				CTUtil.fromMillis(unit, end)));
	}

	@Test
	public void testConvert() {
		final long actual = CTUtil.convert(TimeUnit.SECONDS, TimeUnit.MINUTES, 1);
		Assert.assertEquals(60, actual);
	}

	@Test
	public void testDownTo() {
		final long currentMillis = CTUtil.currentTimeMillis();
		final long baseMillis = CTUtil.toMillis(TimeUnit.HOURS, 1);
		final long actual = CTUtil.downTo(currentMillis, baseMillis);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currentMillis);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		final long expected = calendar.getTimeInMillis();
		Assert.assertEquals("downTo failed: " + CTUtil.formatHHMMSS(expected) + " " + CTUtil.formatHHMMSS(actual),
				expected, actual);
	}

	@Test
	public void testFromMillis() {
		Assert.assertEquals(1, CTUtil.fromMillis(TimeUnit.SECONDS, 1000));
	}

	@Test
	public void testInPeriodFalse() {
		final TimeUnit unit = TimeUnit.MINUTES;
		final long currentMillis = CTUtil.toMillis(unit, 10);
		final int period = 60;
		final int delay = 3;
		Assert.assertFalse(CTUtil.isInPeriod(unit, currentMillis, period, delay));
	}

	@Test
	public void testInPeriodTrue() {
		final TimeUnit unit = TimeUnit.MINUTES;
		final long currentMillis = CTUtil.toMillis(unit, 2);
		final int period = 60;
		final int delay = 3;
		Assert.assertTrue(CTUtil.isInPeriod(unit, currentMillis, period, delay));
	}

	@Test
	public void testMake() {
		final int hour = 14;
		final int min = 20;
		final int sec = 10;
		final long current = CTUtil.make(hour, min, sec);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(current);
		Assert.assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(min, calendar.get(Calendar.MINUTE));
		Assert.assertEquals(sec, calendar.get(Calendar.SECOND));
		Assert.assertEquals(0, calendar.get(Calendar.MILLISECOND));
	}

	@Test
	public void testTimeElapsedFrom() {
		final long current = CTUtil.make(10, 10, 10);
		final long begin = CTUtil.make(10, 0, 0);
		Assert.assertEquals(10, CTUtil.timeElapsedFrom(TimeUnit.MINUTES, current, begin));
	}

	@Test
	public void testTimeRemainsTo() {
		final long current = CTUtil.make(10, 49, 50);
		final long end = CTUtil.make(11, 0, 0);
		Assert.assertEquals(10, CTUtil.timeRemainsTo(TimeUnit.MINUTES, current, end));
	}

	@Test
	public void testToBase() {
		// final long current = CTUtil.make(14, 20, 10);
		final long current = CTUtil.make();
		final long baseMillis = CTUtil.toMillis(TimeUnit.HOURS, 1);
		final long expected = CTUtil.downTo(current, baseMillis);
		final long base = CTUtil.toBase(expected, current, baseMillis);
		final long base2 = CTUtil.toBase(expected - (baseMillis * 2), current, baseMillis);
		final long base3 = CTUtil.toBase(expected + (baseMillis * 2), current, baseMillis);
		Assert.assertEquals("Base failed:" + CTUtil.formatHHMMSS(current) + " " + CTUtil.formatHHMMSS(base), expected,
				base);
		Assert.assertEquals("Base(2) failed:" + CTUtil.formatHHMMSS(current) + " " + CTUtil.formatHHMMSS(base2),
				expected, base2);
		Assert.assertEquals("Base(3) failed:" + CTUtil.formatHHMMSS(current) + " " + CTUtil.formatHHMMSS(base3),
				expected, base3);
	}

	@Test
	public void testToMillis() {
		Assert.assertEquals(1000, CTUtil.toMillis(TimeUnit.SECONDS, 1));
	}

	@Test
	public void testUpTo() {
		final long currentMillis = CTUtil.currentTimeMillis();
		final long baseMillis = CTUtil.toMillis(TimeUnit.HOURS, 1);
		final long actual = CTUtil.upTo(currentMillis, baseMillis);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currentMillis);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.add(Calendar.HOUR, 1);
		final long expected = calendar.getTimeInMillis();
		Assert.assertEquals("upTo failed: " + CTUtil.formatHHMMSS(expected) + " " + CTUtil.formatHHMMSS(actual),
				expected, actual);
	}
}
