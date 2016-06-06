package gargoyle.ct;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class CTSerializationTest {
	@SuppressWarnings("unchecked")
	private static <T> T deserialize(final byte[] bytes) throws ClassNotFoundException, IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		final ObjectInputStream ois = new ObjectInputStream(bais);
		final Object o = ois.readObject();
		ois.close();
		return (T) o;
	}

	private static <T> T pipe(final T orig) throws ClassNotFoundException, IOException {
		final byte[] serialized = CTSerializationTest.serialize(orig);
		// System.out.println(new String(serialized));
		return CTSerializationTest.deserialize(serialized);
	}

	private static <T> byte[] serialize(final T o) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.flush();
		oos.close();
		return baos.toByteArray();
	}

	@Test
	public void testConfigSerialization() throws Exception {
		final CTConfig orig = new CTConfig(TimeUnit.MINUTES, 60, 10, 3);
		final CTConfig restored = CTSerializationTest.pipe(orig);
		Assert.assertEquals(orig, restored);
	}

	@Test
	public void testConfigsSerialization() throws Exception {
		final CTConfigs orig = new CTStandardConfigs();
		final CTConfigs restored = CTSerializationTest.pipe(orig);
		Assert.assertEquals(orig, restored);
	}

	@Test
	public void testLoadH() {
		final CTConfigs parsed = CTConfigs.parse("60H/10H/3H\n30H/5H/3H\n120H/20H/3H\n");
		Assert.assertNotNull(parsed);
		Assert.assertNotNull(parsed.getConfig("3600/600"));
	}

	@Test
	public void testLoadM() {
		final CTConfigs parsed = CTConfigs.parse("60M/10M/3M\n30M/5M/3M\n120M/20M/3M\n");
		Assert.assertNotNull(parsed);
		Assert.assertNotNull(parsed.getConfig("60/10"));
	}

	@Test
	public void testLoadS() {
		final CTConfigs parsed = CTConfigs.parse("3600S/600S/3S\n1800S/300S/3S\n120S/20S/3S\n");
		Assert.assertNotNull(parsed);
		Assert.assertNotNull(parsed.getConfig("60/10"));
	}
}