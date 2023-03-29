package gargoyle.ct.ui.impl.control

import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.ui.CTIconProvider
import gargoyle.ct.util.prop.PropertyObservableDelegate
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JLabel

class CTIconContent(preferences: CTPreferences, iconProvider: CTIconProvider) : JLabel() {
    init {
        updateIcon(iconProvider)
        @Suppress("UNUSED_VARIABLE")
        var iconStyle by PropertyObservableDelegate(
            preferences::iconStyle
        ) { _, _, _ ->
            updateIcon(iconProvider)
        }
        border = BorderFactory.createLineBorder(Color.BLACK)
    }

    fun updateIcon(iconProvider: CTIconProvider) {
        val icon = iconProvider.bigIcon
        requireNotNull(icon) { "image not found" }
        setIcon(ImageIcon(icon))
    }
}
