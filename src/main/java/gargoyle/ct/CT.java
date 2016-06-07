package gargoyle.ct;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CT implements CTApp {
	private static final String LOC_MESSAGES = "messages";
	private static final String CONFIG_NAME = "CT.cfg";

	private static String convertStreamToString(final InputStream is) {
		try (Scanner scanner = new Scanner(is, StandardCharsets.US_ASCII.name())) {
			final Scanner s = scanner.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void main(final String[] args) {
		final CT app = new CT();
		if ((args != null) && (args.length == 1)) {
			app.setFakeTime(CTUtil.parseHHMMSS(args[0]));
			app.blocker.debug(true);
		}
		app.start();
	}

	private final CTBlocker blocker;
	private final CTControlActions control;
	private final ResourceBundle messages;
	private final TimeHelper timeHelper;
	private final CTTimer timer;

	private CT() {
		this.timeHelper = new CTTimeHelper();
		this.messages = ResourceBundle.getBundle(CT.LOC_MESSAGES);
		final CTBlocker pBlocker = new CTBlocker(this);
		final CTControl pControl = new CTControl(this);
		this.timer = new CTTimer(this.timeHelper, pBlocker, pControl);
		this.blocker = pBlocker;
		this.control = pControl;
	}

	@Override
	public void arm(final CTConfig config) {
		this.timer.arm(config, this.timeHelper.currentTimeMillis());
	}

	@Override
	public void exit() {
		this.blocker.dispose();
		this.timer.terminate();
	}

	@Override
	public CTConfigs getConfigs() {
		CTConfigs configs;
		final CTConfigResource configResource = CTConfigResource.findLocal(CT.CONFIG_NAME);
		if (configResource.exists()) {
			try (InputStream stream = (configResource.getInputStream())) {
				configs = CTConfigs.parse(CT.convertStreamToString(stream));
				if (configs.getConfigs().isEmpty()) {
					configs = new CTStandardConfigs();
				}
			} catch (final IOException ex) {
				Log.error(ex, "Cannot load {0}", configResource);
				configs = new CTStandardConfigs();
			}
		} else {
			Log.warn("Not found {0}", configResource);
			configs = new CTStandardConfigs();
		}
		return configs;
	}

	@Override
	public String getMessage(final String message, final Object... args) {
		final String pattern = this.messages.getString(message);
		try {
			return MessageFormat.format(pattern, args);
		} catch (final IllegalArgumentException ex) {
			throw new RuntimeException(
					"can't parse message:" + message + "->" + pattern + "(" + Arrays.toString(args) + ")", ex);
		}
	}

	private void setFakeTime(final long fakeTime) {
		this.timeHelper.setFakeTime(fakeTime);
	}

	private void start() {
		this.control.arm(this.getConfigs().getConfigs().iterator().next());
	}

	@Override
	public void unarm() {
		this.timer.unarm();
	}
}
