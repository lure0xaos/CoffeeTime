package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.convert.impl.CTConfigConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static gargoyle.ct.config.CTStandardConfigs.BLOCK_10M;
import static gargoyle.ct.config.CTStandardConfigs.WARN_3M;
import static gargoyle.ct.config.CTStandardConfigs.WHOLE_1H;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CTConfigTest {
    private static final String CONFIG = "60M/10M/3M";
    private CTConfig config;
    private CTConfigConverter configConverter;

    @Test
    public void format() {
        assertEquals(CONFIG, configConverter.format(TimeUnit.MINUTES, config), "format differs");
    }

    @Test
    public void isNotValid() {
        assertFalse(config.isNotValid(), "why not valid");
    }

    @Test
    public void parse() {
        CTConfig parsed = configConverter.parse(CONFIG);
        assertEquals(config, parsed, "buggy parsing");
    }

    @BeforeEach
    public void setUp() {
        configConverter = new CTConfigConverter();
        config = new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M);
    }
}
