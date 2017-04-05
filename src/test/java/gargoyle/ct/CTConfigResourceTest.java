package gargoyle.ct;

import gargoyle.ct.resource.impl.CTConfigResource;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CTConfigResourceTest {
    private static final String CT_CFG = "CT.cfg";

    @Test
    public void testResource() throws MalformedURLException {
        CTConfigResource resource = CTConfigResource.forURL(new URL("http://www.example.com/"));
        assertTrue(resource.exists());
        resource = CTConfigResource.forURL(new URL("file:///"));
        assertTrue(resource.exists());
        resource = CTConfigResource.findLocal(CT_CFG);
        assertNotNull(resource);
        assertTrue(resource.exists());
    }
}
