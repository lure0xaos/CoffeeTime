package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.util.CTSerializationUtil;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static gargoyle.ct.config.CTStandardConfigs.BLOCK_10M;
import static gargoyle.ct.config.CTStandardConfigs.WARN_3M;
import static gargoyle.ct.config.CTStandardConfigs.WHOLE_1H;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationUtilsTest {
    private static final String STRING = "string";

    @Test
    public void testPipe() throws Exception {
        test(STRING);
        test(Color.BLACK);
        test(new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M));
    }

    private void test(Object obj) throws IOException {
        assertEquals(obj, CTSerializationUtil.pipe(obj), String.format("%s piped (de)serialization wrong", obj));
    }
}
