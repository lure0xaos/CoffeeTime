package gargoyle.ct;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CTTimeUtil {
	private static final String HH_MM_SS = "HH:mm:ss";
	private static final String MM = "mm";
	private static final String MM_SS = "mm:ss";
	private static final String SS = "ss";

	public static long convert(final TimeUnit unitTo, final TimeUnit unitFrom, final long cnt) {
		return unitTo.convert(unitFrom.toMillis(cnt), TimeUnit.MILLISECONDS);
	}

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
		return CTTimeUtil.format(CTTimeUtil.HH_MM_SS, currentMillis);
	}

	public static String formatMM(final long currentMillis) {
		return CTTimeUtil.format(CTTimeUtil.MM, currentMillis);
	}

	public static String formatMMSS(final long currentMillis) {
		return CTTimeUtil.format(CTTimeUtil.MM_SS, currentMillis);
	}

	public static String formatSS(final long currentMillis) {
		return CTTimeUtil.format(CTTimeUtil.SS, currentMillis);
	}

	public static long fromMillis(final TimeUnit unit, final long millis) {
		return unit.convert(millis, TimeUnit.MILLISECONDS);
	}

	public static boolean isBetween(final long currentMillis, final long startMillis, final long endMillis) {
		return (currentMillis >= startMillis) && (currentMillis <= endMillis);
	}

	public static boolean isBetween(final TimeUnit unit, final long current, final long start, final long end) {
		return (CTTimeUtil.toMillis(unit, current) >= CTTimeUtil.toMillis(unit, start))
				&& (CTTimeUtil.toMillis(unit, current) <= CTTimeUtil.toMillis(unit, end));
	}

	public static boolean isInPeriod(final TimeUnit unit, final long currentMillis, final int period, final int delay) {
		return (CTTimeUtil.fromMillis(unit, currentMillis) % period) < delay;
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

	public static long parseHHMMSS(final String string) {
		final String[] pair = string.split(":");
		return CTTimeUtil.make(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]), Integer.parseInt(pair[2]));
	}

	public static long timeElapsedFrom(final long currentMillis, final long begin) {
		return currentMillis - begin;
	}

	public static long timeElapsedFrom(final TimeUnit unit, final long currentMillis, final long begin) {
		return CTTimeUtil.fromMillis(unit, CTTimeUtil.timeElapsedFrom(currentMillis, begin));
	}

	public static long timeRemainsTo(final long currentMillis, final long end) {
		return end - currentMillis;
	}

	public static long timeRemainsTo(final TimeUnit unit, final long currentMillis, final long end) {
		return CTTimeUtil.fromMillis(unit, CTTimeUtil.timeRemainsTo(currentMillis, end));
	}

	public static long toBase(final long startMillis, final long currentMillis, final long baseMillis) {
		final long low = CTTimeUtil.downTo(currentMillis, baseMillis);
		final long high = CTTimeUtil.upTo(currentMillis, baseMillis);
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
		return CTTimeUtil.downTo(currentMillis, baseMillis) + baseMillis;
	}

	private CTTimeUtil() {
		super();
	}
}
