package gargoyle.ct;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CT implements CTApp {
	public static void main(final String[] args) {
		final CT app = new CT();
		if ((args != null) && (args.length == 1)) {
			app.setFakeTime(CTUtil.parseHHMMSS(args[0]));
			app.blocker.debug(true);
		}
		app.start();
	}

	private final CTTimer timer;
	private final CTControlActions control;
	private final CTBlocker blocker;
	private final ResourceBundle messages;
	private final TimeHelper timeHelper;

	private CT() {
		this.timeHelper = new CTTimeHelper();
		this.messages = ResourceBundle.getBundle("messages");
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
	public List<CTConfig> getConfigs() {
		return Arrays.asList(new CTConfig[] { CTStandardConfigs.get6010Config(), CTStandardConfigs.get3005Config(),
				CTStandardConfigs.get12020Config() });
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
		this.control.arm(CTStandardConfigs.get6010Config());
	}

	@Override
	public void unarm() {
		this.timer.unarm();
	}
}
