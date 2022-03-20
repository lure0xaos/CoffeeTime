package gargoyle.ct.ui.impl.control

import java.beans.PropertyChangeListener
import javax.swing.AbstractButton
import javax.swing.Action
import javax.swing.Icon
import javax.swing.event.SwingPropertyChangeSupport

abstract class CTAction : Action {
    private val values: MutableMap<String, Any> = HashMap()
    private var changeSupport: SwingPropertyChangeSupport? = null

    constructor(text: String) {
        this.text = text
        toolTipText = text
        isEnabled = true
    }

    constructor(text: String, icon: Icon) {
        this.text = text
        toolTipText = text
        setIcon(icon)
        isEnabled = true
    }

    constructor(text: String, tooltipText: String, icon: Icon) {
        this.text = text
        toolTipText = tooltipText
        setIcon(icon)
        isEnabled = true
    }

    constructor(text: String, tooltipText: String) {
        this.text = text
        toolTipText = tooltipText
        isEnabled = true
    }

    protected constructor() {
        isEnabled = true
    }

    open fun init(menuItem: AbstractButton) {
        menuItem.action = this
        menuItem.text = text
        menuItem.toolTipText = toolTipText
        val icon = icon
        if (icon != null) {
            menuItem.icon = icon
        }
    }

    protected var text: String
        get() = getValue(Action.NAME).toString()
        protected set(text) {
            putValue(Action.NAME, text)
        }
    protected var toolTipText: String
        get() = getValue(Action.SHORT_DESCRIPTION).toString()
        protected set(text) {
            putValue(Action.SHORT_DESCRIPTION, text)
        }
    protected val icon: Icon?
        get() = getValue(Action.SMALL_ICON) as Icon?

    protected fun setIcon(icon: Icon) {
        putValue(Action.SMALL_ICON, icon)
    }

    override fun getValue(key: String): Any? {
        return values[key]
    }

    override fun putValue(key: String, value: Any) {
        val oldValue = getValue(key)
        values[key] = value
        firePropertyChange(key, oldValue, value)
    }

    override fun isEnabled(): Boolean {
        val value = getValue(ENABLED)
        return value != null && value as Boolean
    }

    override fun setEnabled(b: Boolean) {
        putValue(ENABLED, b)
    }

    @Synchronized
    protected fun firePropertyChange(propertyName: String?, oldValue: Any?, newValue: Any?) {
        if (changeSupport == null || oldValue != null && oldValue == newValue) {
            return
        }
        changeSupport!!.firePropertyChange(propertyName, oldValue, newValue)
    }

    @Synchronized
    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
        if (changeSupport == null) {
            changeSupport = SwingPropertyChangeSupport(this)
        }
        changeSupport!!.addPropertyChangeListener(listener)
    }

    @Synchronized
    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
        if (changeSupport == null) {
            return
        }
        changeSupport!!.removePropertyChangeListener(listener)
    }

    companion object {
        private const val ENABLED = "enabled"
    }
}
