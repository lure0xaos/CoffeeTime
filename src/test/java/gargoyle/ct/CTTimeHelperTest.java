package gargoyle.ct;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CTTimeHelperTest {
	private CTTimeHelper helper = null;

	@Before
	public void setUp() throws Exception {
		this.helper = new CTTimeHelper();
	}

	@After
	public void tearDown() throws Exception {
		this.helper = null;
	}

	@Test
	public void testFakeTime() {
		final long fakeTime = CTTimeUtil.currentTimeMillis();
		this.helper.setFakeTime(fakeTime);
		Assert.assertEquals(fakeTime, this.helper.getFakeTime());
		final long expected = CTTimeUtil.currentTimeMillis();
		final long actual = this.helper.currentTimeMillis();
		Assert.assertTrue((expected - actual) <= 0);
	}
}
