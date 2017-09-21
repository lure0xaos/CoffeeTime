package gargoyle.ct.task.impl

import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.helper.CTTimeHelper
import java.util.*

class CTTimerTask(private val timeHelper: CTTimeHelper, private val updatables: Iterable<CTTaskUpdatable>) :
    TimerTask() {
    val task: CTTask = CTTask()

    constructor(timeHelper: CTTimeHelper, vararg updatables: CTTaskUpdatable) :
            this(timeHelper, listOf<CTTaskUpdatable>(*updatables))

    override fun run() {
        if (task.config != null) {
            updatables.forEach { it.doUpdate(task, timeHelper.currentTimeMillis) }
        }
    }
}
