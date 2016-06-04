package gargoyle.ct;

import java.util.concurrent.TimeUnit;

public class CTStandardConfigs {
	private static CTConfig config12020 = new CTConfig(TimeUnit.MINUTES, 120, 20, 3);
	private static CTConfig config6010 = new CTConfig(TimeUnit.MINUTES, 60, 10, 3);
	private static CTConfig config3005 = new CTConfig(TimeUnit.MINUTES, 30, 5, 3);

	public static CTConfig get12020Config() {
		return CTStandardConfigs.config12020;
	}

	public static CTConfig get3005Config() {
		return CTStandardConfigs.config3005;
	}

	public static CTConfig get6010Config() {
		return CTStandardConfigs.config6010;
	}
}
