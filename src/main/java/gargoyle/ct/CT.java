package gargoyle.ct;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CT implements CTApp {
	public static void main(final String[] args) {
		new CT().start();
	}

	private final CTTimer timer;
	private final CTControlActions control;
	private final CTBlocker blocker;
	private final ResourceBundle messages;

	private CT() {
		this.messages = ResourceBundle.getBundle("messages");
		final CTBlocker pBlocker = new CTBlocker(this);
		final CTControl pControl = new CTControl(this);
		this.timer = new CTTimer(pBlocker, pControl);
		this.blocker = pBlocker;
		this.control = pControl;
	}

	@Override
	public void arm(final CTConfig config) {
		this.timer.arm(config);
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
		return MessageFormat.format(this.messages.getString(message), args);
	}

	private void start() {
		this.control.arm(CTStandardConfigs.get6010Config());
	}

	@Override
	public void unarm() {
		this.timer.unarm();
	}
}
