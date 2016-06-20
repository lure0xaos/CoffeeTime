package gargoyle.ct;

import java.net.URL;

public class URLResource extends VirtualResource {
	public URLResource(final URL url) {
		super(url.toExternalForm());
	}
}
