package gargoyle.ct.ui.impl.control

import gargoyle.ct.messages.LocaleProvider
import java.io.Serial
import javax.swing.JMenuItem

class CTLocalizableMenuItem(provider: LocaleProvider, action: CTLocalizableAction) : JMenuItem(action) {
    init {
        provider.locale()
            .addPropertyChangeListener { update(action) }
        action.init(this)
    }

    private fun update(action: CTLocalizableAction) {
        text = action.localizedText
        toolTipText = action.toolTipLocalizedText
    }

    companion object {
        @Serial
        private val serialVersionUID = -3628913101849581469L
    }
}
