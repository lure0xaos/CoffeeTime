package gargoyle.ct.ui.impl.control

import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.ui.CTIconProvider
import gargoyle.ct.util.Defend
import java.awt.Color
import java.io.Serial
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
        Defend.notNull(icon, "image not found")
        setIcon(ImageIcon(icon))
    }

    companion object {
        @Serial
        private val serialVersionUID = -91984016343905171L
    }
}
