package gargoyle.ct.config.convert.impl

import gargoyle.ct.util.util.CTTimeUtil
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.MessageFormat
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class CTConfigDataConverter(private val unit: TimeUnit = TimeUnit.MINUTES) : KSerializer<LongArray> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ConfigData", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LongArray {
        val data = decoder.decodeString()
        require(data.isNotBlank()) { MSG_EMPTY_LINE }
        val trimmedLine = data.trim()
        require(!COMMENTS.contains(trimmedLine[0])) { ("Commented line: $data") }
        val longs = LongArray(3)
        val m = PATTERN_PARSE.matcher(trimmedLine)
        if (m.find()) {
            val groupCount = m.groupCount()
            require(groupCount == 6) { "Cannot parse line: $data" }
            var g = 1
            while (g <= groupCount) {
                val q = m.group(g)
                val unit: TimeUnit = when (val u = m.group(g + 1)) {
                    UNIT_HOURS -> TimeUnit.HOURS
                    UNIT_MINUTES -> TimeUnit.MINUTES
                    UNIT_SECONDS -> TimeUnit.SECONDS
                    else -> throw IllegalArgumentException("Cannot parse line: $data, invalid time unit $u")
                }
                try {
                    longs[g / 2] = CTTimeUtil.toMillis(unit, q.toLong())
                } catch (ex: NumberFormatException) {
                    throw IllegalArgumentException("Cannot parse line: $data", ex)
                }
                g += 2
            }
        } else {
            throw IllegalArgumentException(data)
        }
        return longs
    }


    override fun serialize(encoder: Encoder, value: LongArray) {
        val unitChar: String = when (unit) {
            TimeUnit.HOURS -> UNIT_HOURS
            TimeUnit.MINUTES -> UNIT_MINUTES
            TimeUnit.SECONDS -> UNIT_SECONDS
            TimeUnit.DAYS, TimeUnit.MILLISECONDS, TimeUnit.MICROSECONDS, TimeUnit.NANOSECONDS ->
                throw UnsupportedOperationException("Cannot parse line, invalid time unit ${TimeUnit.MINUTES.name}")

            else ->
                throw UnsupportedOperationException("Cannot parse line, invalid time unit ${TimeUnit.MINUTES.name}")
        }
        return encoder.encodeString(
            MessageFormat.format(
                PATTERN_FORMAT,
                CTTimeUtil.fromMillis(TimeUnit.MINUTES, value[0]),
                unitChar,
                CTTimeUtil.fromMillis(TimeUnit.MINUTES, value[1]),
                unitChar,
                CTTimeUtil.fromMillis(TimeUnit.MINUTES, value[2]),
                unitChar
            )
        )
    }

    companion object {
        private const val COMMENTS = "#;'"
        private const val MSG_EMPTY_LINE = "Empty line"
        private const val PATTERN_FORMAT = "{0}{1}/{2}{3}/{4}{5}"
        private val PATTERN_PARSE = Pattern.compile(
            "(?:([0-9]+)([a-zA-Z]+))/(?:([0-9]+)([a-zA-Z]+))[^a-zA-Z0-9]+(?:([0-9]+)([a-zA-Z]+))",
            Pattern.CASE_INSENSITIVE
        )
        private const val UNIT_HOURS = "H"
        private const val UNIT_MINUTES = "M"
        private const val UNIT_SECONDS = "S"
    }
}
