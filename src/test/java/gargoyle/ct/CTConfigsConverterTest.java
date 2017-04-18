package gargoyle.ct;

import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.config.CTStandardConfigs;
import gargoyle.ct.config.convert.CTUnitConverter;
import gargoyle.ct.config.convert.impl.CTConfigsConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CTConfigsConverterTest {

    @Test
    public void testConvert() {
        CTUnitConverter<CTConfigs> converter = new CTConfigsConverter();
        CTConfigs                  config    = new CTConfigs(new CTStandardConfigs().getConfigs());
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.MINUTES, config)), config);
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.HOURS, config)), config);
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.SECONDS, config)), config);
    }
}
