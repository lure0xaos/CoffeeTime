package gargoyle.ct.ui

import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.impl.CTTask

abstract class CTStateUpdatable : CTTaskUpdatable {
    private var state: State? = null
    override fun doUpdate(task: CTTask, currentMillis: Long) {
        if (task.isSleeping(currentMillis)) {
            changeState(state, State.SLEEP)
        }
        if (task.isWarn(currentMillis)) {
            changeState(state, State.WARN)
        }
        if (task.isBlocked(currentMillis)) {
            changeState(state, State.BLOCK)
        }
    }

    private fun changeState(oldState: State?, newState: State) {
        if (oldState != newState) {
            state = newState
            onStateChange(oldState, newState)
        }
    }

    protected abstract fun onStateChange(oldState: State?, newState: State?)
    protected enum class State {
        SLEEP, WARN, BLOCK
    }
}
