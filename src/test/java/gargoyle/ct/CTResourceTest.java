package gargoyle.ct;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class CTResourceTest {
	@Test
	public void testResourceExt() {
		final Resource resource = new ClasspathResource("rb.properties").withExtension("txt");
		final String url = resource.getLocation();
		System.out.println(url);
		Assert.assertTrue(url.endsWith(".txt"));
	}

	@Test
	public void testResourceExtLocale() {
		final Resource resource = new ClasspathResource("rb.properties").withExtension("txt")
				.forLocale(new Locale("ru"));
		final String url = resource.getLocation();
		System.out.println(url);
		Assert.assertTrue(url.endsWith(".txt"));
	}

	@Test
	public void testResourceLocale() {
		final Resource resource = new ClasspathResource("rb.properties").forLocale(new Locale("ru"));
		final String url = resource.getLocation();
		System.out.println(url);
		Assert.assertTrue(url.endsWith(".properties"));
	}
}
