package gargoyle.ct.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.concurrent.TimeUnit

object CTTimeUtil {
    private const val HH_MM_SS = "HH:mm:ss"
    private const val MM = "mm"
    private const val MM_SS = "mm:ss"
    private const val SS = "ss"

    @JvmStatic
    fun convert(unitTo: TimeUnit, unitFrom: TimeUnit, cnt: Long): Long {
        return unitTo.convert(unitFrom.toMillis(cnt), TimeUnit.MILLISECONDS)
    }

    @JvmStatic
    fun currentTimeMillis(): Long {
        return Date().time
    }

    @JvmStatic
    fun formatHHMMSS(currentMillis: Long): String {
        return format(HH_MM_SS, currentMillis)
    }

    private fun format(format: String, currentMillis: Long): String {
        return Instant.ofEpochMilli(currentMillis).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(format))
    }

    @JvmStatic
    fun formatMM(currentMillis: Long): String {
        return format(MM, currentMillis)
    }

    @JvmStatic
    fun formatMMSS(currentMillis: Long): String {
        return format(MM_SS, currentMillis)
    }

    @JvmStatic
    fun formatSS(currentMillis: Long): String {
        return format(SS, currentMillis)
    }

    fun isBetween(currentMillis: Long, startMillis: Long, endMillis: Long): Boolean {
        return currentMillis in startMillis..endMillis
    }

    @JvmStatic
    fun isBetween(unit: TimeUnit, current: Long, start: Long, end: Long): Boolean {
        return toMillis(unit, current) >= toMillis(unit, start) && toMillis(unit, current) <= toMillis(unit, end)
    }

    @JvmStatic
    fun toMillis(unit: TimeUnit, duration: Long): Long {
        return unit.toMillis(duration)
    }

    @JvmStatic
    fun isInPeriod(unit: TimeUnit, currentMillis: Long, period: Int, delay: Int): Boolean {
        return fromMillis(unit, currentMillis) % period < delay
    }

    @JvmStatic
    fun fromMillis(unit: TimeUnit, millis: Long): Long {
        return unit.convert(millis, TimeUnit.MILLISECONDS)
    }

    fun make(): Long {
        return LocalDateTime.now().withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    @JvmStatic
    fun parseHHMMSS(string: String?): Long {
        val pair = string!!.split(":").toTypedArray()
        return make(pair[0].toInt(), pair[1].toInt(), pair[2].toInt())
    }

    @JvmStatic
    fun make(hours: Int, minutes: Int, seconds: Int): Long {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(hours, minutes, seconds))
            .withNano(0)
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    }

    fun timeElapsedFrom(unit: TimeUnit, currentMillis: Long, begin: Long): Long {
        return fromMillis(unit, timeElapsedFrom(currentMillis, begin))
    }

    private fun timeElapsedFrom(currentMillis: Long, begin: Long): Long {
        return currentMillis - begin
    }

    fun timeRemainsTo(unit: TimeUnit, currentMillis: Long, end: Long): Long {
        return fromMillis(unit, timeRemainsTo(currentMillis, end))
    }

    fun timeRemainsTo(currentMillis: Long, end: Long): Long {
        return end - currentMillis
    }

    @JvmStatic
    fun toBase(startMillis: Long, currentMillis: Long, baseMillis: Long): Long {
        val low = downTo(currentMillis, baseMillis)
        val high = upTo(currentMillis, baseMillis)
        var ret = startMillis
        while (ret <= low) {
            ret += baseMillis
        }
        while (ret >= high) {
            ret -= baseMillis
        }
        return ret
    }

    @JvmStatic
    fun downTo(currentMillis: Long, baseMillis: Long): Long {
        return currentMillis / baseMillis * baseMillis
    }

    @JvmStatic
    fun upTo(currentMillis: Long, baseMillis: Long): Long {
        return downTo(currentMillis, baseMillis) + baseMillis
    }
}
