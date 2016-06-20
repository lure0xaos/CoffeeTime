package gargoyle.ct;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CTTaskTest {
	private CTTask task;
	private long actual;

	@Before
	public void setUp() throws Exception {
		this.task = new CTTask();
	}

	@After
	public void tearDown() throws Exception {
		this.task = null;
	}

	@Test
	public void testBlockedFalse() {
		final long current = CTTimeUtil.make(10, 45, 10);
		Assert.assertFalse(this.task.isBlocked(current));
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertFalse("testBlocked failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
				this.task.isBlocked(current));
	}

	@Test
	public void testBlockedTrue() {
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTTimeUtil.make(10, 55, 10);
		final long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertTrue("testBlocked failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
				this.task.isBlocked(current));
	}

	@Test
	public void testSleepingFalse() {
		final long current = CTTimeUtil.make(10, 51, 10);
		Assert.assertFalse(this.task.isWarn(current));
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertFalse("testSleeping failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
				this.task.isSleeping(current));
	}

	@Test
	public void testSleepingTrue() {
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTTimeUtil.make(10, 30, 10);
		final long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertTrue("testSleeping failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
				this.task.isSleeping(current));
	}

	@Test
	public void testStarted() {
		final long started = CTTimeUtil.make();
		this.task.setStarted(started);
		this.actual = this.task.getStarted();
		Assert.assertEquals(started, this.actual);
	}

	@Test
	public void testStartedUnits() {
		final long started = CTTimeUtil.make();
		final TimeUnit unit = TimeUnit.SECONDS;
		this.task.setStarted(unit, started);
		this.actual = this.task.getStarted(unit);
		Assert.assertEquals(started, this.actual);
	}

	@Test
	public void testWarnFalse() {
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTTimeUtil.make(10, 45, 10);
		final long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertFalse("testWarn failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
				this.task.isWarn(current));
	}

	@Test
	public void testWarnTrue() {
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTTimeUtil.make(10, 49, 10);
		final long started = CTTimeUtil.downTo(current, CTTimeUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertTrue("testWarn failed: " + CTTimeUtil.formatHHMMSS(current) + " " + CTTimeUtil.formatHHMMSS(started),
				this.task.isWarn(current));
	}
}
