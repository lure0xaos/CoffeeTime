package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.util.SerializationUtils;
import org.junit.Test;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SerializationUtilsTest {

    private void test(Object obj) throws Exception {
        assertEquals(obj, SerializationUtils.pipe(obj));
    }

    @Test
    public void testPipe() throws Exception {
        test("string");
        test(Color.BLACK);
        test(new CTConfig(TimeUnit.MINUTES, 60, 10, 3));
    }
}
