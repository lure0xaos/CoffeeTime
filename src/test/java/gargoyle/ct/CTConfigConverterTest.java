package gargoyle.ct;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CTConfigConverterTest {
	private CTConfigConverter converter = null;

	@Before
	public void setUp() throws Exception {
		this.converter = CTConfigConverter.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		this.converter = null;
	}

	@Test
	public void testConvert() {
		final CTConfig config = new CTConfig(TimeUnit.MINUTES, 60, 10, 3);
		Assert.assertEquals(this.converter.parse(this.converter.format(TimeUnit.MINUTES, config)), config);
		Assert.assertEquals(this.converter.parse(this.converter.format(TimeUnit.HOURS, config)), config);
		Assert.assertEquals(this.converter.parse(this.converter.format(TimeUnit.SECONDS, config)), config);
	}
}
