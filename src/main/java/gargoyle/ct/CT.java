package gargoyle.ct;

import java.util.Arrays;
import java.util.List;

public class CT implements CTControlActions {
	public static void main(final String[] args) {
		new CT().start();
	}

	private final CTTimer timer;
	private final CTControlActions control;
	private final CTBlocker blocker;

	private CT() {
		final CTBlocker blocker = new CTBlocker();
		final CTControl control = new CTControl(this);
		this.timer = new CTTimer(blocker, control);
		this.blocker = blocker;
		this.control = control;
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

	private void start() {
		this.control.arm(CTStandardConfigs.get6010Config());
	}

	@Override
	public void unarm() {
		this.timer.unarm();
	}
}
