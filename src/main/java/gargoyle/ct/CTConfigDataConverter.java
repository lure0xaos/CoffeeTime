package gargoyle.ct;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public final class CTConfigDataConverter implements Converter<long[]> {
	private static CTConfigDataConverter instance;

	public static synchronized CTConfigDataConverter getInstance() {
		if (CTConfigDataConverter.instance == null) {
			CTConfigDataConverter.instance = new CTConfigDataConverter();
		}
		return CTConfigDataConverter.instance;
	}

	private CTConfigDataConverter() {
		super();
	}

	@Override
	public String format(final TimeUnit unit, final long[] data) {
		String unitChar;
		switch (unit) {
		case HOURS:
			unitChar = "H";
			break;
		case MINUTES:
			unitChar = "M";
			break;
		case SECONDS:
			unitChar = "S";
			break;
		default:
			throw new UnsupportedOperationException(unit.name());
		}
		return CTUtil.fromMillis(unit, data[0]) + unitChar + "/" + CTUtil.fromMillis(unit, data[1]) + unitChar + "/"
				+ CTUtil.fromMillis(unit, data[2]) + unitChar;
	}

	@Override
	public long[] parse(final String line) {
		final long[] data = new long[3];
		final String[] parts = line.split(Pattern.quote("/"), 3);
		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			final char u = part.charAt(part.length() - 1);
			TimeUnit unit;
			switch (u) {
			case 'H':
				unit = TimeUnit.HOURS;
				break;
			case 'M':
				unit = TimeUnit.MINUTES;
				break;
			case 'S':
				unit = TimeUnit.SECONDS;
				break;
			default:
				throw new IllegalArgumentException(String.valueOf(u));
			}
			data[i] = CTUtil.toMillis(unit, Long.parseLong(part.substring(0, part.length() - 1)));
		}
		return data;
	}
}