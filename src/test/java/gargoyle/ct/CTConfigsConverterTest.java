package gargoyle.ct;

import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.config.CTStandardConfigs;
import gargoyle.ct.config.convert.CTConfigsConverter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CTConfigsConverterTest {
    private CTConfigsConverter converter;

    @Before
    public void setUp() {
        converter = CTConfigsConverter.getInstance();
    }

    @After
    public void tearDown() {
        converter = null;
    }

    @Test
    public void testConvert() {
        CTConfigs config = new CTConfigs(new CTStandardConfigs().getConfigs());
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.MINUTES, config)), config);
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.HOURS, config)), config);
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.SECONDS, config)), config);
    }
}
