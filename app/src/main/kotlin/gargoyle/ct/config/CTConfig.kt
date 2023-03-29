package gargoyle.ct.config

import gargoyle.ct.config.convert.impl.CTConfigConverter
import gargoyle.ct.util.util.CTTimeUtil
import kotlinx.serialization.Serializable
import java.text.MessageFormat
import java.util.concurrent.TimeUnit

@Serializable(with = CTConfigConverter::class)
data class CTConfig(
    val block: Long,
    var name: String,
    val warn: Long,
    val whole: Long
) {

    constructor() : this(
        whole = 0L,
        block = 0L,
        warn = 0L,
        name = STR_INVALID
    )

    fun getWhole(unit: TimeUnit): Long = CTTimeUtil.fromMillis(unit, whole)

    constructor(whole: Long, block: Long, warn: Long) : this(
        whole = whole,
        block = block,
        warn = warn,
        name = name(TimeUnit.MINUTES, whole, block)
    ) {
        require(isValid(whole, block, warn)) { "$MSG_NOT_VALID ($whole, $block, $warn)" }
    }

    constructor(unit: TimeUnit, whole: Long, block: Long, warn: Long) : this(
        CTTimeUtil.toMillis(unit, whole),
        CTTimeUtil.toMillis(unit, block),
        CTTimeUtil.toMillis(unit, warn)
    ) {
        name = name(unit, CTTimeUtil.toMillis(unit, whole), CTTimeUtil.toMillis(unit, block))
    }

    fun isValid(wholeMillis: Long, blockMillis: Long, warnMillis: Long): Boolean =
        blockMillis in (warnMillis + 1) until wholeMillis

    fun getBlock(unit: TimeUnit): Long = CTTimeUtil.fromMillis(unit, block)

    fun getWarn(unit: TimeUnit): Long = CTTimeUtil.fromMillis(unit, warn)

    val isValid: Boolean
        get() = isValid(whole, block, warn)

    override fun hashCode(): Int =
        31 * (31 * (31 * 1 + (block xor block ushr 32).toInt()) + (warn xor warn ushr 32).toInt()) + (whole xor whole ushr 32).toInt()

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other == null -> false
            javaClass != other.javaClass -> false
            else -> block == (other as CTConfig).block && warn == other.warn && whole == other.whole
        }

    override fun toString(): String =
        MessageFormat.format("CTConfig [name={0}, whole={1}, block={2}, warn={3}]", name, whole, block, warn)

    fun name(unit: TimeUnit): String = name(unit, whole, block)

    init {
        require(isValid) { "invalid configuration" }
    }

    companion object {
        private const val FORMAT_NAME = "{0,number,00}/{1,number,00}"
        private const val MSG_NOT_VALID = "convert is not valid"
        private const val STR_INVALID = "invalid"

        private fun name(unit: TimeUnit, whole: Long, block: Long): String =
            MessageFormat.format(
                FORMAT_NAME,
                CTTimeUtil.fromMillis(unit, whole),
                CTTimeUtil.fromMillis(unit, block)
            )
    }
}
