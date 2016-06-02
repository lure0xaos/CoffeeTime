package gargoyle.ct;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CTUtil {
	public static final long currentTimeMillis() {
		return new Date().getTime();
	}

	public static long downTo(final long currentMillis, final long baseMillis) {
		return (currentMillis / baseMillis) * baseMillis;
	}

	public static String format(final String format, final long currentMillis) {
		return new SimpleDateFormat(format).format(new Date(currentMillis));
	}

	public static String formatHHMMSS(final long currentMillis) {
		return CTUtil.format("hh:mm:ss", currentMillis);
	}

	public static String formatMM(final long currentMillis) {
		return CTUtil.format("mm", currentMillis);
	}

	public static String formatMMSS(final long currentMillis) {
		return CTUtil.format("mm:ss", currentMillis);
	}

	public static String formatSS(final long currentMillis) {
		return CTUtil.format("ss", currentMillis);
	}

	public static long fromMillis(final TimeUnit unit, final long millis) {
		return unit.convert(millis, TimeUnit.MILLISECONDS);
	}

	public static boolean isBetween(final long currentMillis, final long start, final long end) {
		return (currentMillis >= start) && (currentMillis <= end);
	}

	public static long make() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static long make(final int hours, final int minutes, final int seconds) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, seconds);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static long timeElapsedFrom(final long currentMillis, final long begin) {
		return currentMillis - begin;
	}

	public static long timeElapsedFrom(final TimeUnit unit, final long currentMillis, final long begin) {
		return CTUtil.fromMillis(unit, CTUtil.timeElapsedFrom(currentMillis, begin));
	}

	public static long timeRemainsTo(final long currentMillis, final long end) {
		return end - currentMillis;
	}

	public static long timeRemainsTo(final TimeUnit unit, final long currentMillis, final long end) {
		return CTUtil.fromMillis(unit, CTUtil.timeRemainsTo(currentMillis, end));
	}

	public static long toBase(final long startMillis, final long currentMillis, final long baseMillis) {
		final long low = CTUtil.downTo(currentMillis, baseMillis);
		final long high = CTUtil.upTo(currentMillis, baseMillis);
		long ret = startMillis;
		while (ret <= low) {
			ret += baseMillis;
		}
		while (ret >= high) {
			ret -= baseMillis;
		}
		return ret;
	}

	public static long toMillis(final TimeUnit unit, final long duration) {
		return unit.toMillis(duration);
	}

	public static long upTo(final long currentMillis, final long baseMillis) {
		return CTUtil.downTo(currentMillis, baseMillis) + baseMillis;
	}

	private CTUtil() {
		super();
	}
}
