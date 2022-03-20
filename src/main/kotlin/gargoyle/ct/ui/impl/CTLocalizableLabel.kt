package gargoyle.ct.ui.impl

import gargoyle.ct.messages.LocaleProvider
import gargoyle.ct.messages.MessageProvider
import java.io.Serial
import javax.swing.JLabel

class CTLocalizableLabel(
    messages: MessageProvider, provider: LocaleProvider, textKey: String, toolTipTextKey: String,
    alignment: Int
) : JLabel(textKey, alignment) {
    init {
        provider.locale().addPropertyChangeListener {
            update(
                messages,
                textKey,
                toolTipTextKey
            )
        }
        update(messages, textKey, toolTipTextKey)
        icon = null
        horizontalAlignment = alignment
    }

    private fun update(messages: MessageProvider, textKey: String, toolTipTextKey: String) {
        text = messages.getMessage(textKey)
        toolTipText = messages.getMessage(toolTipTextKey)
    }

    companion object {
        @Serial
        private val serialVersionUID = 1215028617118416457L
    }
}
