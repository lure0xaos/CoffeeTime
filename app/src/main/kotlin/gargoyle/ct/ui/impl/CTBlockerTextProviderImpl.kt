package gargoyle.ct.ui.impl

import gargoyle.ct.messages.locale.CTPreferencesLocaleProvider
import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.task.impl.CTTask
import gargoyle.ct.ui.CTApp
import gargoyle.ct.ui.CTBlockerTextProvider
import gargoyle.ct.util.messages.MessageProvider
import gargoyle.ct.util.messages.impl.CTMessages
import gargoyle.ct.util.util.CTTimeUtil
import gargoyle.ct.util.util.getResourceBundle
import java.awt.Color
import java.util.concurrent.TimeUnit

class CTBlockerTextProviderImpl(preferences: CTPreferences) : CTBlockerTextProvider {
    private val messages: MessageProvider

    init {
        messages = CTMessages(
            { CTApp::class.getResourceBundle(LOC_MESSAGES, it) },
            CTPreferencesLocaleProvider(preferences)
        )
    }

    override fun getBlockerText(task: CTTask, currentMillis: Long, big: Boolean): String {
        if (task.isReady) {
            if (task.isBlocked(currentMillis)) {
                return messages.getMessage(
                    if (big) STR_BLOCKED_BIG else STR_BLOCKED_SMALL,
                    CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockEnd(currentMillis)))
                )
            }
            if (task.isWarn(currentMillis)) {
                return if (CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, PERIOD, DELAY)) messages.getMessage(
                    if (big) STR_WARN_BIG else STR_WARN_SMALL,
                    CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis)))
                ) else ""
            }
        }
        return ""
    }

    override fun getColor(task: CTTask, currentMillis: Long): Color {
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

    override fun getInfoText(currentMillis: Long): String = CTTimeUtil.formatHHMMSS(currentMillis)

    override fun getToolTipText(task: CTTask, currentMillis: Long): String {
        var toolTipText = CTTimeUtil.formatHHMMSS(currentMillis)
        if (task.isReady) {
            if (task.isBlocked(currentMillis)) {
                toolTipText = CTTimeUtil.formatMMSS(
                    CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockEnd(currentMillis))
                )
            }
            if (task.isWarn(currentMillis)) {
                toolTipText = CTTimeUtil.formatMMSS(
                    CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis))
                )
            }
            if (task.isSleeping(currentMillis)) {
                toolTipText = CTTimeUtil.formatMMSS(
                    CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis))
                )
            }
        }
        return toolTipText
    }

    override fun isVisible(task: CTTask, currentMillis: Long): Boolean {
        when {
            task.isReady -> {
                when {
                    task.isBlocked(currentMillis) -> {
                        return true
                    }

                    task.isWarn(currentMillis) -> {
                        return CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, PERIOD, DELAY)
                    }
                }
            }

            else -> return false
        }
        return false
    }

    companion object {
        private const val DELAY = 3
        private const val LOC_MESSAGES = "blocker"
        private const val PERIOD = 60
        private const val STR_BLOCKED_BIG = "blocked_w-big"
        private const val STR_BLOCKED_SMALL = "blocked_w-small"
        private const val STR_WARN_BIG = "warn_w-big"
        private const val STR_WARN_SMALL = "warn_w-small"
    }
}
