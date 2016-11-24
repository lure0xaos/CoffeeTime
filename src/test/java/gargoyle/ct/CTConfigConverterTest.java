package gargoyle.ct;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CTConfigConverterTest {
	private CTConfigConverter converter;

	@Before
	public void setUp()  {
        converter = CTConfigConverter.getInstance();
	}

	@After
	public void tearDown()  {
        converter = null;
	}

	@Test
	public void testConvert() {
		CTConfig config = new CTConfig(TimeUnit.MINUTES, 60, 10, 3);
		Assert.assertEquals(converter.parse(converter.format(TimeUnit.MINUTES, config)), config);
		Assert.assertEquals(converter.parse(converter.format(TimeUnit.HOURS, config)), config);
		Assert.assertEquals(converter.parse(converter.format(TimeUnit.SECONDS, config)), config);
	}
}
