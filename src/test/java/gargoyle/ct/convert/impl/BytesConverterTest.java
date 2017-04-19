package gargoyle.ct.convert.impl;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class BytesConverterTest {

    @Test
    public void testConverter() {
        BytesConverter converter = new BytesConverter();
        byte[]         original  = new byte[0];
        String         formatted = converter.format(original);
        byte[]         parsed    = converter.parse(formatted);
        assertArrayEquals(MessageFormat.format("{0}!={1}", Arrays.toString(parsed), Arrays.toString(original)),
                          original,
                          parsed);
    }
}
