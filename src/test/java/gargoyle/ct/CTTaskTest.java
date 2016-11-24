package gargoyle.ct;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CTTaskTest {
	private CTTask task;
	private long actual;

	@Before
	public void setUp()  {
        task = new CTTask();
	}

	@After
	public void tearDown()  {
        task = null;
	}

	@Test
	public void testBlockedFalse() {
		long current = CTTimeUtil.make(10, 45, 10);
		Assert.assertFalse(task.isBlocked(current));
        task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
		Assert.assertFalse("testBlocked failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
            task.isBlocked(current));
	}

	@Test
	public void testBlockedTrue() {
        task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		long current = CTTimeUtil.make(10, 55, 10);
		long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
		Assert.assertTrue("testBlocked failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
            task.isBlocked(current));
	}

	@Test
	public void testSleepingFalse() {
		long current = CTTimeUtil.make(10, 51, 10);
		Assert.assertFalse(task.isWarn(current));
        task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
		Assert.assertFalse("testSleeping failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
            task.isSleeping(current));
	}

	@Test
	public void testSleepingTrue() {
        task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		long current = CTTimeUtil.make(10, 30, 10);
		long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
		Assert.assertTrue("testSleeping failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
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
        task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		long current = CTTimeUtil.make(10, 45, 10);
		long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
		Assert.assertFalse("testWarn failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
            task.isWarn(current));
	}

	@Test
	public void testWarnTrue() {
        task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		long current = CTTimeUtil.make(10, 49, 10);
		long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
        task.setStarted(started);
		Assert.assertTrue("testWarn failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
            task.isWarn(current));
	}
}
