package gargoyle.ct.ui.impl.control

import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.ui.CTIconProvider
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JLabel

class CTIconContent(preferences: CTPreferences, iconProvider: CTIconProvider) : JLabel() {
    init {
        updateIcon(iconProvider)
        preferences.iconStyle()
            .addPropertyChangeListener { updateIcon(iconProvider) }
        border = BorderFactory.createLineBorder(Color.BLACK)
    }

    fun updateIcon(iconProvider: CTIconProvider) {
        val icon = iconProvider.bigIcon
        requireNotNull(icon) { "image not found" }
        setIcon(ImageIcon(icon))
    }
}
