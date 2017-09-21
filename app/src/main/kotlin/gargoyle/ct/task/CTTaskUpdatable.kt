package gargoyle.ct.task

import gargoyle.ct.task.impl.CTTask

fun interface CTTaskUpdatable {
    fun doUpdate(task: CTTask, currentMillis: Long)
}
