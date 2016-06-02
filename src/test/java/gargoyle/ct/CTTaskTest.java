package gargoyle.ct;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CTTaskTest {
	private CTTask task;

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
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTUtil.make(10, 45, 10);
		final long started = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertFalse("testBlocked failed: " + CTUtil.formatHHMMSS(current) + " " + CTUtil.formatHHMMSS(started),
				this.task.isBlocked(current));
	}

	@Test
	public void testBlockedTrue() {
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTUtil.make(10, 55, 10);
		final long started = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertTrue("testBlocked failed: " + CTUtil.formatHHMMSS(current) + " " + CTUtil.formatHHMMSS(started),
				this.task.isBlocked(current));
	}

	@Test
	public void testSleepingFalse() {
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTUtil.make(10, 51, 10);
		final long started = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertFalse("testSleeping failed: " + CTUtil.formatHHMMSS(current) + " " + CTUtil.formatHHMMSS(started),
				this.task.isSleeping(current));
	}

	@Test
	public void testSleepingTrue() {
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTUtil.make(10, 30, 10);
		final long started = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertTrue("testSleeping failed: " + CTUtil.formatHHMMSS(current) + " " + CTUtil.formatHHMMSS(started),
				this.task.isSleeping(current));
	}

	@Test
	public void testWarnFalse() {
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTUtil.make(10, 45, 10);
		final long started = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertFalse("testWarn failed: " + CTUtil.formatHHMMSS(current) + " " + CTUtil.formatHHMMSS(started),
				this.task.isWarn(current));
	}

	@Test
	public void testWarnTrue() {
		this.task.setConfig(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
		final long current = CTUtil.make(10, 49, 10);
		final long started = CTUtil.downTo(current, CTUtil.toMillis(TimeUnit.HOURS, 1));
		this.task.setStarted(started);
		Assert.assertTrue("testWarn failed: " + CTUtil.formatHHMMSS(current) + " " + CTUtil.formatHHMMSS(started),
				this.task.isWarn(current));
	}
}
