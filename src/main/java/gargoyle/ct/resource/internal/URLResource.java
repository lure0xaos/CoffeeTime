package gargoyle.ct.resource.internal;

import java.net.URL;

public class URLResource extends VirtualResource {

    public URLResource(URL url) {
        super(url.toExternalForm());
    }
}
