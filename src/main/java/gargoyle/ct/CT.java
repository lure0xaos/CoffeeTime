package gargoyle.ct;

public class CT {
	public static void main(final String[] args) {
		new CT().start();
	}

	private CTTimer timer;

	private CT() {
		super();
	}

	private void start() {
		this.timer = new CTTimer();
		this.timer.arm(CTStandardConfigs.get6010Config());
	}
}
