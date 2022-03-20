package gargoyle.ct.prop.impl

import gargoyle.ct.log.Log
import gargoyle.ct.pref.CTPropertyChangeEvent
import gargoyle.ct.pref.CTPropertyChangeListener
import gargoyle.ct.prop.CTProperty
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

object CTPropertyChangeManager {
    private val listeners: MutableMap<CTProperty<*>?, MutableList<CTPropertyChangeListener<*>>> = ConcurrentHashMap()
    fun <T : Any> addPropertyChangeListener(property: CTProperty<T>, listener: CTPropertyChangeListener<T>) {
        val list: MutableList<CTPropertyChangeListener<*>>
        if (listeners.containsKey(property)) {
            list = listeners[property]!!
        } else {
            list = CopyOnWriteArrayList()
            listeners[property] = list
        }
        list.add(listener)
    }

    fun <T : Any> firePropertyChange(property: CTProperty<T>, event: CTPropertyChangeEvent<T>): Thread? {
        if (listeners.containsKey(property)) {
            val listeners: List<CTPropertyChangeListener<*>> = listeners[property]!!
            val thread = Thread({
                for (listener in listeners) {
                    try {
                        @Suppress("UNCHECKED_CAST")
                        (listener as CTPropertyChangeListener<T>).onPropertyChange(event)
                    } catch (ex: RuntimeException) {
                        Log.error(ex, MSG_ERROR_INVOKING_LISTENER)
                    }
                }
            }, STR_PROPERTY_CHANGE_LISTENER)
            thread.start()
            return thread
        }
        return null
    }

    fun <T : Any> removePropertyChangeListener(property: CTProperty<T>, listener: CTPropertyChangeListener<T>) {
        if (listeners.containsKey(property)) listeners[property]!!.add(listener)
    }

    fun removePropertyChangeListeners() {
        listeners.clear()
    }

    private const val STR_PROPERTY_CHANGE_LISTENER = "CTPropertyChangeListener#onPropertyChange"
    private const val MSG_ERROR_INVOKING_LISTENER = "Error invoking $STR_PROPERTY_CHANGE_LISTENER"

    @get:Synchronized
    var instance: CTPropertyChangeManager = CTPropertyChangeManager

}
