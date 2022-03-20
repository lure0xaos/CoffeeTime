package gargoyle.ct.ui.impl.control

import gargoyle.ct.messages.MessageProvider
import javax.swing.AbstractButton
import javax.swing.Icon

abstract class CTLocalizableAction : CTAction {
    private val messages: MessageProvider

    constructor(messages: MessageProvider, textKey: String) {
        this.messages = messages
        this.textKey = textKey
        toolTipTextKey = textKey
        isEnabled = true
    }

    constructor(messages: MessageProvider, textKey: String, icon: Icon?) {
        this.messages = messages
        this.textKey = textKey
        toolTipTextKey = textKey
        setIcon(icon!!)
        isEnabled = true
    }

    constructor(messages: MessageProvider, textKey: String, tooltipTextKey: String, icon: Icon?) {
        this.messages = messages
        this.textKey = textKey
        toolTipTextKey = tooltipTextKey
        setIcon(icon!!)
        isEnabled = true
    }

    protected constructor(messages: MessageProvider, textKey: String, tooltipTextKey: String) {
        this.messages = messages
        this.textKey = textKey
        toolTipTextKey = tooltipTextKey
        isEnabled = true
    }

    override fun init(menuItem: AbstractButton) {
        super.init(menuItem)
        menuItem.text = localizedText
        menuItem.toolTipText = toolTipLocalizedText
    }

    val localizedText: String
        get() {
            val textKey: String = textKey
            return if (textKey.isEmpty()) "" else messages.getMessage(textKey)
        }
    private var textKey: String
        get() = getValue(KEY_TEXT).toString()
        private set(textKey) {
            putValue(KEY_TEXT, textKey)
        }
    val toolTipLocalizedText: String
        get() {
            val toolTipTextKey: String = toolTipTextKey
            return if (toolTipTextKey.isEmpty()) "" else messages.getMessage(toolTipTextKey)
        }
    private var toolTipTextKey: String
        get() = getValue(KEY_TOOLTIP).toString()
        private set(toolTipTextKey) {
            putValue(KEY_TOOLTIP, toolTipTextKey)
        }

    companion object {
        private const val KEY_TEXT = "text-key"
        private const val KEY_TOOLTIP = "tooltip-key"
    }
}
