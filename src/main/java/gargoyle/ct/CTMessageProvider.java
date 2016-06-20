package gargoyle.ct;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class CTMessageProvider implements MessageProvider {
	private ResourceBundle messages;
	private final LocaleProvider localeProvider = new LocaleProvider();

	public CTMessageProvider(final String baseName) {
		this(baseName, Locale.getDefault());
	}

	public CTMessageProvider(final String baseName, final Locale locale) {
		this.localeProvider.setLocale(locale);
		this.load(baseName);
	}

	private void check() {
		if (!this.messages.getLocale().equals(this.localeProvider.getLocale())) {
			this.reload();
		}
	}

	public Locale getLocale() {
		return this.localeProvider.getLocale();
	}

	@Override
	public String getMessage(final String message, final Object... args) {
		this.check();
		final String pattern = this.messages.getString(message);
		try {
			return MessageFormat.format(pattern, args);
		} catch (final IllegalArgumentException ex) {
			throw new RuntimeException(
					"can't parse message:" + message + "->" + pattern + "(" + Arrays.toString(args) + ")", ex);
		}
	}

	private void load(final String baseName) {
		this.messages = ResourceBundle.getBundle(baseName, this.localeProvider.getLocale(),
				ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES));
	}

	private void reload() {
		this.load(this.messages.getBaseBundleName());
	}

	public void setLocale(final Locale locale) {
		this.localeProvider.setLocale(locale);
		this.reload();
	}
}
