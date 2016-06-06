package gargoyle.ct;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class CTConfigResourceTest {
	@Test
	public void testResource() throws Exception {
		CTConfigResource resource;
		resource = CTConfigResource.forURL(new URL("http://www.example.com/"));
		Assert.assertTrue(resource.exists());
		resource = CTConfigResource.forURL(new URL("file:///"));
		Assert.assertTrue(resource.exists());
		resource = CTConfigResource.findLocal(("CT.cfg"));
		Assert.assertTrue(resource.exists());
	}
}
