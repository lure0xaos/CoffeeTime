package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.config.CTStandardConfigs;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

import static gargoyle.ct.config.CTStandardConfigs.BLOCK_10M;
import static gargoyle.ct.config.CTStandardConfigs.WARN_3M;
import static gargoyle.ct.config.CTStandardConfigs.WHOLE_1H;

public class CTSerializationTest {

    @SuppressWarnings({"unchecked", "MethodCanBeVariableArityMethod"})
    private static <T> T deserialize(byte[] bytes) throws ClassNotFoundException, IOException {
        try (final ObjectInput ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (T) ois.readObject();
        }
    }

    private static <T> T pipe(T orig) throws ClassNotFoundException, IOException {
        byte[] serialized = serialize(orig);
        // System.out.println(new String(serialized));
        return deserialize(serialized);
    }

    private static <T> byte[] serialize(T o) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             final ObjectOutput oos = new ObjectOutputStream(out)) {
            oos.writeObject(o);
            return out.toByteArray();
        }
    }

    @Test
    public void testConfigSerialization() throws IOException, ClassNotFoundException {
        CTConfig orig = new CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M);
        CTConfig restored = pipe(orig);
        Assert.assertEquals(orig, restored);
    }

    @Test
    public void testConfigsSerialization() throws IOException, ClassNotFoundException {
        CTConfigs orig = new CTStandardConfigs();
        CTConfigs restored = pipe(orig);
        Assert.assertEquals(orig, restored);
    }

    @Test
    public void testLoadH() {
        CTConfigs parsed = CTConfigs.parse("60H/10H/3H\n30H/5H/3H\n120H/20H/3H\n"); //NON-NLS
        Assert.assertNotNull(parsed);
        Assert.assertNotNull(parsed.getConfig("3600/600"));
    }

    @Test
    public void testLoadM() {
        CTConfigs parsed = CTConfigs.parse("60M/10M/3M\n30M/5M/3M\n120M/20M/3M\n"); //NON-NLS
        Assert.assertNotNull(parsed);
        Assert.assertNotNull(parsed.getConfig("60/10"));
    }

    @Test
    public void testLoadS() {
        CTConfigs parsed = CTConfigs.parse("3600S/600S/3S\n1800S/300S/3S\n120S/20S/3S\n"); //NON-NLS
        Assert.assertNotNull(parsed);
        Assert.assertNotNull(parsed.getConfig("60/10"));
    }
}
