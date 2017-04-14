package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.convert.impl.CTConfigConverter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CTConfigTest {

    private static final String CONFIG = "60M/10M/3M";

    @Test
    public void format() {
        CTConfig config = createConfig();
        assertEquals(CONFIG, new CTConfigConverter().format(TimeUnit.MINUTES, config));
    }

    @SuppressWarnings("MagicNumber")
    private static CTConfig createConfig() {
        return new CTConfig(TimeUnit.MINUTES, 60, 10, 3);
    }

    @Test
    public void isNotValid() {
        CTConfig config = createConfig();
        assertFalse(config.isNotValid());
    }

    @Test
    public void parse() {
        CTConfig config = createConfig();
        CTConfig parsed = new CTConfigConverter().parse(CONFIG);
        assertEquals(config, parsed);
    }
}
