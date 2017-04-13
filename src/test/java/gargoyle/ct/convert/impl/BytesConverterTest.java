package gargoyle.ct.convert.impl;

import gargoyle.ct.pref.impl.convert.impl.BytesConverter;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class BytesConverterTest {
    @Test
    public void testConverter() throws Exception {
        BytesConverter converter = new BytesConverter();
        byte[] original = new byte[0];
        String formatted = converter.format(original);
        byte[] parsed = converter.parse(formatted);
        assertArrayEquals(Arrays.toString(parsed) + "!=" + Arrays.toString(original), original, parsed);
    }
}
