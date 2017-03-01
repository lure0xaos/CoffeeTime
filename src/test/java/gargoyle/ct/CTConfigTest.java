package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CTConfigTest {

    public static final String CONFIG = "60M/10M/3M";

    private CTConfig config;

    @Test
    public void format() throws Exception {
        assertEquals(CONFIG, config.format());
    }

    @Test
    public void isNotValid() throws Exception {
        assertFalse(config.isNotValid());
    }

    @Test
    public void parse() throws Exception {
        CTConfig parsed = CTConfig.parse(CONFIG);
        assertEquals(config, parsed);
    }

    @Before
    public void setUp() {
        config = new CTConfig(TimeUnit.MINUTES, 60, 10, 3);
    }
}
