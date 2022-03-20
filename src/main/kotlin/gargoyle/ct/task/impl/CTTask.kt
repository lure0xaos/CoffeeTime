package gargoyle.ct.task.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.util.CTTimeUtil
import java.util.concurrent.TimeUnit

class CTTask {
    var config: CTConfig? = null
    var started: Long = 0
    fun getStarted(unit: TimeUnit): Long {
        return CTTimeUtil.fromMillis(unit, started)
    }

    fun isBlocked(currentMillis: Long): Boolean {
        return isReady &&
                CTTimeUtil.isBetween(currentMillis, getBlockStart(currentMillis), getBlockEnd(currentMillis))
    }

    fun getBlockStart(currentMillis: Long): Long {
        return getBlockEnd(currentMillis) - config!!.block
    }

    fun getBlockEnd(currentMillis: Long): Long {
        return CTTimeUtil.upTo(CTTimeUtil.toBase(started, currentMillis, config!!.whole), config!!.whole)
    }

    val isReady: Boolean
        get() = started != 0L && config != null

    fun isSleeping(currentMillis: Long): Boolean {
        return CTTimeUtil.isBetween(currentMillis, getCycleStart(currentMillis), getWarnStart(currentMillis))
    }

    private fun getCycleStart(currentMillis: Long): Long {
        return CTTimeUtil.downTo(CTTimeUtil.toBase(started, currentMillis, config!!.whole), config!!.whole)
    }

    private fun getWarnStart(currentMillis: Long): Long {
        return getBlockStart(currentMillis) - config!!.warn
    }

    fun isWarn(currentMillis: Long): Boolean {
        return isReady &&
                CTTimeUtil.isBetween(currentMillis, getWarnStart(currentMillis), getBlockStart(currentMillis))
    }

    fun setStarted(unit: TimeUnit, started: Long) {
        this.started = CTTimeUtil.toMillis(unit, started)
    }
}
