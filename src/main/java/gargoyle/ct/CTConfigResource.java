package gargoyle.ct;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class CTConfigResource implements Resource {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)";
	private static final String PROP_UA = "User-Agent";
	private static final String METHOD_HEAD = "HEAD";

	public static boolean exists(final URL url) {
		try {
			final URLConnection connection = url.openConnection();
			if (connection instanceof HttpURLConnection) {
				final HttpURLConnection huc = (HttpURLConnection) connection;
				huc.setInstanceFollowRedirects(false);
				huc.setRequestMethod(CTConfigResource.METHOD_HEAD);
				huc.setRequestProperty(CTConfigResource.PROP_UA, CTConfigResource.USER_AGENT);
				huc.connect();
				return huc.getResponseCode() == HttpURLConnection.HTTP_OK;
			}
			if ("file".equals(url.getProtocol())) {
				return new File(url.toURI()).exists();
			}
		} catch (final IOException ex) {
			return false;
		} catch (final URISyntaxException ex) {
			return false;
		}
		return false;
	}

	public static CTConfigResource findLocal(final String name) {
		for (final URL root : CTConfigResource.getLocalRoots()) {
			URL curl;
			try {
				curl = new URL(root, name);
			} catch (final MalformedURLException ex) {
				throw new RuntimeException(ex);
			}
			if (CTConfigResource.exists(curl)) {
				return new CTConfigResource(curl);
			}
		}
		return null;
	}

	public static CTConfigResource forURL(final URL url) {
		return new CTConfigResource(url);
	}

	private static URL[] getLocalRoots() {
		try {
			return new URL[] { new File(".").toURI().toURL(),
					new File(System.getProperty("user.dir", ".")).toURI().toURL(),
					new File(System.getProperty("user.home", ".")).toURI().toURL() };
		} catch (final MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
	}

	private final URL url;

	private CTConfigResource(final URL url) {
		super();
		this.url = url;
	}

	@Override
	public boolean exists() {
		return CTConfigResource.exists(this.url);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		final URLConnection connection = this.url.openConnection();
		return connection.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		final URLConnection connection = this.url.openConnection();
		return connection.getOutputStream();
	}

	@Override
	public URL getURL() {
		return this.url;
	}
}
