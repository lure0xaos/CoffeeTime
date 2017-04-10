package gargoyle.ct;

import gargoyle.ct.config.data.CTConfig;
import gargoyle.ct.util.CTSerializationUtil;
import org.junit.Test;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CTSerializationUtilTest {
    @Test
    public void testPipe() throws Exception {
        test("string"); //NON-NLS
        test(Color.BLACK);
        test(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
    }

    private void test(Object obj) throws Exception {
        assertEquals(obj, CTSerializationUtil.pipe(obj));
    }
}
