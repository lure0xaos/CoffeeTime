package gargoyle.ct;

import gargoyle.ct.config.convert.impl.CTConfigsConverter;
import gargoyle.ct.config.data.CTConfigs;
import gargoyle.ct.config.data.CTStandardConfigs;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CTConfigsConverterTest {
    @Test
    public void testConvert() {
        CTConfigsConverter converter = new CTConfigsConverter();
        CTConfigs config = new CTConfigs(new CTStandardConfigs().getConfigs());
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.MINUTES, config)), config);
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.HOURS, config)), config);
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.SECONDS, config)), config);
    }
}
