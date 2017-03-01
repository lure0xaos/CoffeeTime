package gargoyle.ct.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CTTimeUtil {

    private static final String HH_MM_SS = "HH:mm:ss";

    private static final String MM = "mm";

    private static final String MM_SS = "mm:ss";

    private static final String SS = "ss";

    private CTTimeUtil() {
    }

    @SuppressWarnings("SameParameterValue")
    public static long convert(TimeUnit unitTo, TimeUnit unitFrom, long cnt) {
        return unitTo.convert(unitFrom.toMillis(cnt), TimeUnit.MILLISECONDS);
    }

    public static long currentTimeMillis() {
        return new Date().getTime();
    }

    public static long downTo(long currentMillis, long baseMillis) {
        return currentMillis / baseMillis * baseMillis;
    }

    private static String format(String format, long currentMillis) {
        return Instant.ofEpochMilli(currentMillis).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(format));
    }

    public static String formatHHMMSS(long currentMillis) {
        return format(HH_MM_SS, currentMillis);
    }

    public static String formatMM(long currentMillis) {
        return format(MM, currentMillis);
    }

    public static String formatMMSS(long currentMillis) {
        return format(MM_SS, currentMillis);
    }

    public static String formatSS(long currentMillis) {
        return format(SS, currentMillis);
    }

    public static long fromMillis(TimeUnit unit, long millis) {
        return unit.convert(millis, TimeUnit.MILLISECONDS);
    }

    public static boolean isBetween(long currentMillis, long startMillis, long endMillis) {
        return currentMillis >= startMillis && currentMillis <= endMillis;
    }

    public static boolean isBetween(TimeUnit unit, long current, long start, long end) {
        return toMillis(unit, current) >= toMillis(unit, start) && toMillis(unit, current) <= toMillis(unit, end);
    }

    public static boolean isInPeriod(TimeUnit unit, long currentMillis, int period, int delay) {
        return fromMillis(unit, currentMillis) % period < delay;
    }

    public static long make() {
        return LocalDateTime.now().withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli();
        //        Calendar calendar = Calendar.getInstance();
        //        calendar.set(Calendar.MILLISECOND, 0);
        //        return calendar.getTimeInMillis();
    }

    public static long make(int hours, int minutes, int seconds) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(hours, minutes, seconds))
            .withNano(0)
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli();
        //        Calendar calendar = Calendar.getInstance();
        //        calendar.set(Calendar.HOUR_OF_DAY, hours);
        //        calendar.set(Calendar.MINUTE, minutes);
        //        calendar.set(Calendar.SECOND, seconds);
        //        calendar.set(Calendar.MILLISECOND, 0);
        //        return calendar.getTimeInMillis();
    }

    public static long parseHHMMSS(String string) {
        String[] pair = string.split(":");
        return make(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]), Integer.parseInt(pair[2]));
    }

    private static long timeElapsedFrom(long currentMillis, long begin) {
        return currentMillis - begin;
    }

    @SuppressWarnings("SameParameterValue")
    public static long timeElapsedFrom(TimeUnit unit, long currentMillis, long begin) {
        return fromMillis(unit, timeElapsedFrom(currentMillis, begin));
    }

    public static long timeRemainsTo(long currentMillis, long end) {
        return end - currentMillis;
    }

    @SuppressWarnings("SameParameterValue")
    public static long timeRemainsTo(TimeUnit unit, long currentMillis, long end) {
        return fromMillis(unit, timeRemainsTo(currentMillis, end));
    }

    public static long toBase(long startMillis, long currentMillis, long baseMillis) {
        long low = downTo(currentMillis, baseMillis);
        long high = upTo(currentMillis, baseMillis);
        long ret = startMillis;
        while (ret <= low) {
            ret += baseMillis;
        }
        while (ret >= high) {
            ret -= baseMillis;
        }
        return ret;
    }

    public static long toMillis(TimeUnit unit, long duration) {
        return unit.toMillis(duration);
    }

    public static long upTo(long currentMillis, long baseMillis) {
        return downTo(currentMillis, baseMillis) + baseMillis;
    }
}
