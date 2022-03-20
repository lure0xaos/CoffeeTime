package gargoyle.ct.config

import gargoyle.ct.util.CTTimeUtil
import gargoyle.ct.util.Defend
import java.io.InvalidObjectException
import java.io.ObjectInputValidation
import java.io.Serial
import java.text.MessageFormat
import java.util.concurrent.TimeUnit

class CTConfig : ObjectInputValidation {
    val block: Long
    var name: String
        private set
    val warn: Long
    val whole: Long

    constructor() {
//        Defend.isTrue(isValid(0L, 0L, 0L), MSG_NOT_VALID)
        whole = 0L
        block = 0L
        warn = 0L
        name = STR_INVALID
    }

    fun getWhole(unit: TimeUnit): Long {
        return CTTimeUtil.fromMillis(unit, whole)
    }

    constructor(whole: Long, block: Long, warn: Long) {
        Defend.isTrue(isValid(whole, block, warn), "$MSG_NOT_VALID ($whole, $block, $warn)")
        this.whole = whole
        this.block = block
        this.warn = warn
        name = name(TimeUnit.MINUTES, whole, block)
    }

    constructor(unit: TimeUnit, whole: Long, block: Long, warn: Long) : this(
        CTTimeUtil.toMillis(unit, whole),
        CTTimeUtil.toMillis(unit, block),
        CTTimeUtil.toMillis(unit, warn)
    ) {
        name = name(unit, CTTimeUtil.toMillis(unit, whole), CTTimeUtil.toMillis(unit, block))
    }

    fun isValid(wholeMillis: Long, blockMillis: Long, warnMillis: Long): Boolean {
        return blockMillis in (warnMillis + 1) until wholeMillis
    }

    fun getBlock(unit: TimeUnit): Long {
        return CTTimeUtil.fromMillis(unit, block)
    }

    fun getWarn(unit: TimeUnit): Long {
        return CTTimeUtil.fromMillis(unit, warn)
    }

    val isValid: Boolean
        get() = isValid(whole, block, warn)

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (block xor block ushr 32).toInt()
        result = prime * result + (warn xor warn ushr 32).toInt()
        result = prime * result + (whole xor whole ushr 32).toInt()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val ctConfig = other as CTConfig
        return block == ctConfig.block && warn == ctConfig.warn && whole == ctConfig.whole
    }

    override fun toString(): String {
        return MessageFormat.format("CTConfig [name={0}, whole={1}, block={2}, warn={3}]", name, whole, block, warn)
    }

    fun name(unit: TimeUnit): String {
        return name(unit, whole, block)
    }

    @Throws(InvalidObjectException::class)
    override fun validateObject() {
        if (isNotValid) {
            throw InvalidObjectException("invalid configuration")
        }
    }

    val isNotValid: Boolean
        get() = !isValid(whole, block, warn)

    companion object {
        private const val FORMAT_NAME = "{0,number,00}/{1,number,00}"
        private const val MSG_NOT_VALID = "convert is not valid"
        private const val STR_INVALID = "invalid"

        @Serial
        private val serialVersionUID = -898699928298432564L
        private fun name(unit: TimeUnit, whole: Long, block: Long): String {
            return MessageFormat.format(
                FORMAT_NAME,
                CTTimeUtil.fromMillis(unit, whole),
                CTTimeUtil.fromMillis(unit, block)
            )
        }
    }
}
