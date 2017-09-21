package gargoyle.ct.ui

import gargoyle.ct.task.impl.CTTask
import java.awt.Color

interface CTBlockerTextProvider {

    fun getBlockerText(task: CTTask, currentMillis: Long, big: Boolean): String

    fun getColor(task: CTTask, currentMillis: Long): Color

    fun getInfoText(currentMillis: Long): String

    fun getToolTipText(task: CTTask, currentMillis: Long): String

    fun isVisible(task: CTTask, currentMillis: Long): Boolean

}
