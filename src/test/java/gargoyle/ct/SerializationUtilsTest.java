package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.util.SerializationUtils;
import org.junit.Test;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SerializationUtilsTest {
    @Test
    public void testPipe() throws Exception {
        test("string"); //NON-NLS
        test(Color.BLACK);
        test(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
    }

    private void test(Object obj) throws Exception {
        assertEquals(obj, SerializationUtils.pipe(obj));
    }
}
