package gargoyle.ct;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class CTResourceTest {
	@Test
	public void testResourceExt() {
		Resource resource = new ClasspathResource("rb.properties").withExtension("txt");
		String url = resource.getLocation();
		System.out.println(url);
		Assert.assertTrue(url.endsWith(".txt"));
	}

	@Test
	public void testResourceExtLocale() {
		Resource resource = new ClasspathResource("rb.properties").withExtension("txt")
				.forLocale(new Locale("ru"));
		String url = resource.getLocation();
		System.out.println(url);
		Assert.assertTrue(url.endsWith(".txt"));
	}

	@Test
	public void testResourceLocale() {
		Resource resource = new ClasspathResource("rb.properties").forLocale(new Locale("ru"));
		String url = resource.getLocation();
		System.out.println(url);
		Assert.assertTrue(url.endsWith(".properties"));
	}
}
