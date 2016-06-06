package gargoyle.ct;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public interface Resource {
	boolean exists();

	public InputStream getInputStream() throws IOException;

	public OutputStream getOutputStream() throws IOException;

	public URL getURL();
}
