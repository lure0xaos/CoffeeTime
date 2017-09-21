package gargoyle.ct.resource.internal;

import gargoyle.ct.resource.Resource;

import java.net.URL;

/**
 * represents {@link Resource} from local or remote {@link URL}
 */
public class URLResource extends VirtualResource {
    public URLResource(URL url) {
        super(url.toExternalForm());
    }
}
