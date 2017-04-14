package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.util.CTSerializationUtil;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CTSerializationUtilTest {

    @SuppressWarnings("MagicNumber")
    @Test
    public void testPipe() throws IOException {
        test("string"); //NON-NLS
        test(Color.BLACK);
        test(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
    }

    private void test(Object obj) throws IOException {
        assertEquals(obj, CTSerializationUtil.pipe(obj));
    }
}
