package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.convert.impl.CTConfigConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static gargoyle.ct.config.CTStandardConfigs.BLOCK_10M;
import static gargoyle.ct.config.CTStandardConfigs.WARN_3M;
import static gargoyle.ct.config.CTStandardConfigs.WHOLE_1H;

public class CTConfigConverterTest {
    @Test
    public void testConvert() {
        CTConfigConverter converter = new CTConfigConverter();
        CTConfig config = new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M);
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.MINUTES, config)), config);
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.HOURS, config)), config);
        Assert.assertEquals(converter.parse(converter.format(TimeUnit.SECONDS, config)), config);
    }
}
