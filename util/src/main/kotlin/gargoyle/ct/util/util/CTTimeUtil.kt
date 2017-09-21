package gargoyle.ct.util.util

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

object CTTimeUtil {
    private const val HH_MM_SS = "HH:mm:ss"
    private const val MM = "mm"
    private const val MM_SS = "mm:ss"
    private const val SS = "ss"

    fun convert(unitTo: TimeUnit, unitFrom: TimeUnit, cnt: Long): Long =
        unitTo.convert(unitFrom.toMillis(cnt), TimeUnit.MILLISECONDS)

    fun currentTimeMillis(): Long = Date().time

    fun formatHHMMSS(currentMillis: Long): String = format(HH_MM_SS, currentMillis)

    private fun format(format: String, currentMillis: Long): String =
        Instant.ofEpochMilli(currentMillis).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(format))

    fun formatMM(currentMillis: Long): String = format(MM, currentMillis)

    fun formatMMSS(currentMillis: Long): String = format(MM_SS, currentMillis)

    fun formatSS(currentMillis: Long): String = format(SS, currentMillis)

    fun isBetween(currentMillis: Long, startMillis: Long, endMillis: Long): Boolean =
        currentMillis in startMillis..endMillis

    fun isBetween(unit: TimeUnit, current: Long, start: Long, end: Long): Boolean =
        toMillis(unit, current) >= toMillis(unit, start) && toMillis(unit, current) <= toMillis(unit, end)

    fun toMillis(unit: TimeUnit, duration: Long): Long = unit.toMillis(duration)

    fun isInPeriod(unit: TimeUnit, currentMillis: Long, period: Int, delay: Int): Boolean =
        fromMillis(unit, currentMillis) % period < delay

    fun fromMillis(unit: TimeUnit, millis: Long): Long = unit.convert(millis, TimeUnit.MILLISECONDS)

    fun make(): Long = LocalDateTime.now().withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli()

    fun parseHHMMSS(string: String): Long {
        val pair = string.split(':').toTypedArray()
        return make(pair[0].toInt(), pair[1].toInt(), pair[2].toInt())
    }

    fun make(hours: Int, minutes: Int, seconds: Int): Long {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(hours, minutes, seconds))
            .withNano(0)
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    }

    fun timeElapsedFrom(unit: TimeUnit, currentMillis: Long, begin: Long): Long =
        fromMillis(unit, timeElapsedFrom(currentMillis, begin))

    private fun timeElapsedFrom(currentMillis: Long, begin: Long): Long = currentMillis - begin

    fun timeRemainsTo(unit: TimeUnit, currentMillis: Long, end: Long): Long =
        fromMillis(unit, timeRemainsTo(currentMillis, end))

    fun timeRemainsTo(currentMillis: Long, end: Long): Long = end - currentMillis

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

    fun downTo(currentMillis: Long, baseMillis: Long): Long = currentMillis / baseMillis * baseMillis

    fun upTo(currentMillis: Long, baseMillis: Long): Long = downTo(currentMillis, baseMillis) + baseMillis
}
