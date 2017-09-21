package gargoyle.ct.task.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.helper.CTTimeHelper
import gargoyle.ct.util.util.className
import java.util.*

class CTTimer(timeHelper: CTTimeHelper, updatables: Iterable<CTTaskUpdatable>) {
    private val timer: Timer = Timer(CTTimer::class.className, true)
    private val timerTask: CTTimerTask

    constructor(timeHelper: CTTimeHelper, vararg updatables: CTTaskUpdatable) :
            this(timeHelper, listOf<CTTaskUpdatable>(*updatables))

    init {
        timerTask = CTTimerTask(timeHelper, updatables)
        timer.scheduleAtFixedRate(timerTask, CHECK_DELAY.toLong(), CHECK_PERIOD.toLong())
    }

    fun arm(config: CTConfig, currentMillis: Long): Unit =
        with(timerTask.task) {
            this.config = config
            this.started = currentMillis
        }

    fun terminate(): Unit = timer.cancel()

    fun unarm() {
        timerTask.task.started = 0
    }

    companion object {
        private const val CHECK_DELAY = 100
        private const val CHECK_PERIOD = 500
    }
}
