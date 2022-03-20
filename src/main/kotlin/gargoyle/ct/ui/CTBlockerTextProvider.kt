package gargoyle.ct.ui

import gargoyle.ct.messages.MessageProvider
import gargoyle.ct.messages.impl.CTMessages
import gargoyle.ct.messages.impl.CTPreferencesLocaleProvider
import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.task.impl.CTTask
import gargoyle.ct.util.CTTimeUtil
import java.awt.Color
import java.util.concurrent.TimeUnit

class CTBlockerTextProvider(preferences: CTPreferences) {
    private val messages: MessageProvider

    init {
        messages = CTMessages(CTPreferencesLocaleProvider(preferences), LOC_MESSAGES)
    }

    fun getBlockerText(task: CTTask, currentMillis: Long, big: Boolean): String {
        if (task.isReady) {
            if (task.isBlocked(currentMillis)) {
                return messages.getMessage(
                    if (big) STR_BLOCKED_BIG else STR_BLOCKED_SMALL,
                    CTTimeUtil.formatMMSS(
                        CTTimeUtil.timeRemainsTo(
                            currentMillis,
                            task.getBlockEnd(currentMillis)
                        )
                    )
                )
            }
            if (task.isWarn(currentMillis)) {
                return if (CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, PERIOD, DELAY)) messages.getMessage(
                    if (big) STR_WARN_BIG else STR_WARN_SMALL,
                    CTTimeUtil.formatMMSS(
                        CTTimeUtil.timeRemainsTo(
                            currentMillis,
                            task.getBlockStart(currentMillis)
                        )
                    )
                ) else ""
            }
        }
        return ""
    }

    fun getColor(task: CTTask, currentMillis: Long): Color {
        if (task.isReady) {
            if (task.isBlocked(currentMillis)) {
                return Color.WHITE
            }
            if (task.isWarn(currentMillis)) {
                return if (CTTimeUtil.isInPeriod(
                        TimeUnit.SECONDS,
                        currentMillis,
                        PERIOD,
                        DELAY
                    )
                ) Color.GREEN else Color.WHITE
            }
        }
        return Color.WHITE
    }

    fun getInfoText(currentMillis: Long): String {
        return CTTimeUtil.formatHHMMSS(currentMillis)
    }

    fun getToolTipText(task: CTTask, currentMillis: Long): String {
        var toolTipText = CTTimeUtil.formatHHMMSS(currentMillis)
        if (task.isReady) {
            if (task.isBlocked(currentMillis)) {
                toolTipText = CTTimeUtil.formatMMSS(
                    CTTimeUtil.timeRemainsTo(
                        currentMillis,
                        task.getBlockEnd(currentMillis)
                    )
                )
            }
            if (task.isWarn(currentMillis)) {
                toolTipText = CTTimeUtil.formatMMSS(
                    CTTimeUtil.timeRemainsTo(
                        currentMillis,
                        task.getBlockStart(currentMillis)
                    )
                )
            }
            if (task.isSleeping(currentMillis)) {
                toolTipText = CTTimeUtil.formatMMSS(
                    CTTimeUtil.timeRemainsTo(
                        currentMillis,
                        task.getBlockStart(currentMillis)
                    )
                )
            }
        }
        return toolTipText
    }

    fun isVisible(task: CTTask, currentMillis: Long): Boolean {
        if (task.isReady) {
            if (task.isBlocked(currentMillis)) {
                return true
            }
            if (task.isWarn(currentMillis)) {
                return CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, PERIOD, DELAY)
            }
        }
        return false
    }

    companion object {
        private const val DELAY = 3
        private const val LOC_MESSAGES = "messages.blocker"
        private const val PERIOD = 60
        private const val STR_BLOCKED_BIG = "blocked_w-big"
        private const val STR_BLOCKED_SMALL = "blocked_w-small"
        private const val STR_WARN_BIG = "warn_w-big"
        private const val STR_WARN_SMALL = "warn_w-small"
    }
}
