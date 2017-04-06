package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CTConfigTest {
    private static final String CONFIG = "60M/10M/3M";

    @Test
    public void format() throws Exception {
        CTConfig config = new CTConfig(TimeUnit.MINUTES, 60, 10, 3);
        assertEquals(CONFIG, config.format());
    }

    @Test
    public void isNotValid() throws Exception {
        CTConfig config = new CTConfig(TimeUnit.MINUTES, 60, 10, 3);
        assertFalse(config.isNotValid());
    }

    @Test
    public void parse() throws Exception {
        CTConfig config = new CTConfig(TimeUnit.MINUTES, 60, 10, 3);
        CTConfig parsed = CTConfig.parse(CONFIG);
        assertEquals(config, parsed);
    }
}
