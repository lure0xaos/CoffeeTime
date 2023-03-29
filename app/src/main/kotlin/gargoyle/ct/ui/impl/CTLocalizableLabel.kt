package gargoyle.ct.ui.impl

import gargoyle.ct.util.messages.LocaleProvider
import gargoyle.ct.util.messages.MessageProvider
import gargoyle.ct.util.prop.PropertyObservableDelegate
import java.util.*
import javax.swing.JLabel
import kotlin.reflect.KProperty

class CTLocalizableLabel(
    messages: MessageProvider, provider: LocaleProvider, textKey: String, toolTipTextKey: String,
    alignment: Int
) : JLabel(textKey, alignment) {
    init {
        @Suppress("UNUSED_VARIABLE")
        val providerLocale by PropertyObservableDelegate(provider::locale) { _: KProperty<*>, _: Locale, _: Locale ->
            update(messages, textKey, toolTipTextKey)
        }
        update(messages, textKey, toolTipTextKey)
        icon = null
        horizontalAlignment = alignment
    }

    private fun update(messages: MessageProvider, textKey: String, toolTipTextKey: String) {
        text = messages.getMessage(textKey)
        toolTipText = messages.getMessage(toolTipTextKey)
    }
}
