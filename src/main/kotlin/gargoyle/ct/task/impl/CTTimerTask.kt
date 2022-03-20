package gargoyle.ct.task.impl

import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.helper.CTTimeHelper
import java.util.TimerTask

class CTTimerTask(private val timeHelper: CTTimeHelper, private val updatables: Iterable<CTTaskUpdatable>) :
    TimerTask() {
    val task = CTTask()

    constructor(timeHelper: CTTimeHelper, vararg updatables: CTTaskUpdatable) : this(
        timeHelper,
        listOf<CTTaskUpdatable>(*updatables)
    )

    override fun run() {
        for (updatable in updatables) {
            updatable.doUpdate(task, timeHelper.currentTimeMillis())
        }
    }
}
