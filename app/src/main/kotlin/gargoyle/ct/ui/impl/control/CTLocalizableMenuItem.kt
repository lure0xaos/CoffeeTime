package gargoyle.ct.ui.impl.control

import gargoyle.ct.util.messages.LocaleProvider
import gargoyle.ct.util.prop.PropertyObservableDelegate
import javax.swing.JMenuItem

class CTLocalizableMenuItem(provider: LocaleProvider, action: CTLocalizableAction) : JMenuItem(action) {
    init {
        @Suppress("UNUSED_VARIABLE")
        var providerLocale by PropertyObservableDelegate(provider::locale) { _, _, _ ->
            update(action)
        }
        action.init(this)
    }

    private fun update(action: CTLocalizableAction) {
        text = action.localizedText
        toolTipText = action.toolTipLocalizedText
    }
}
