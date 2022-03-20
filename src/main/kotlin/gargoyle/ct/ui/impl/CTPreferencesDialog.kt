package gargoyle.ct.ui.impl

import gargoyle.ct.messages.MessageProviderEx
import gargoyle.ct.messages.impl.CTMessages
import gargoyle.ct.messages.impl.CTPreferencesLocaleProvider
import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.pref.CTPreferences.IconStyle
import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.prop.CTProperty
import gargoyle.ct.ui.CTApp
import gargoyle.ct.ui.CTDialog
import gargoyle.ct.ui.util.CTDragHelper
import gargoyle.ct.ui.util.CTLayoutBuilder
import java.awt.Container
import java.awt.Window
import java.io.Serial
import javax.swing.ImageIcon
import javax.swing.JDialog
import javax.swing.JSlider

class CTPreferencesDialog(app: CTApp, owner: Window?) : JDialog(owner, ModalityType.MODELESS), CTDialog<Void?> {
    init {
        val preferences = app.preferences
        setIconImage(ImageIcon(app.smallIcon).image)
        init(CTMessages(CTPreferencesLocaleProvider(preferences), LOC_MESSAGES), preferences, contentPane)
        pack()
        defaultCloseOperation = HIDE_ON_CLOSE
    }

    private fun init(messages: MessageProviderEx, preferences: CTPreferences, pane: Container) {
        val layoutBuilder = CTLayoutBuilder(pane)
        title = messages.getMessage(STR_TITLE)
        layoutBuilder.addLabeledControl(
            layoutBuilder.createLocalizableLabel(messages, STR_BLOCK, STR_BLOCK_TOOLTIP),
            layoutBuilder.createCheckBox(preferences.block())
        )
        layoutBuilder.addLabeledControl(
            layoutBuilder.createLocalizableLabel(messages, STR_SOUND, STR_SOUND_TOOLTIP),
            layoutBuilder.createCheckBox(preferences.sound())
        )
        layoutBuilder.addLabeledControl(
            layoutBuilder.createLocalizableLabel(
                messages,
                STR_TRANSPARENCY,
                STR_TRANSPARENCY_TOOLTIP
            ),
            layoutBuilder.createCheckBox(preferences.transparency())
        )
        layoutBuilder.addLabeledControl(
            layoutBuilder.createLocalizableLabel(
                messages,
                STR_TRANSPARENCY_LEVEL,
                STR_TRANSPARENCY_LEVEL_TOOLTIP
            ),
            createTransparencyLevelControl(layoutBuilder, preferences.transparencyLevel())
        )
        layoutBuilder.addLabeledControl(
            layoutBuilder.createLocalizableLabel(
                messages,
                STR_ICON_STYLE,
                STR_ICON_STYLE_TOOLTIP
            ),
            layoutBuilder.createComboBox(
                messages, IconStyle::class,
                preferences.iconStyle(),
                false
            )
        )
        layoutBuilder.addLabeledControl(
            layoutBuilder.createLocalizableLabel(
                messages,
                STR_SUPPORTED_LOCALES,
                STR_SUPPORTED_LOCALES_TOOLTIP
            ),
            layoutBuilder.createComboBox(
                messages, SupportedLocales::class,
                preferences.supportedLocales(),
                false
            )
        )
        layoutBuilder.build()
    }

    override fun showMe(): Void? {
        isVisible = true
        CTDragHelper.setLocationRelativeTo(this, owner)
        requestFocus()
        return null
    }

    companion object {
        private const val LOC_MESSAGES = "messages.preferences"
        private const val STR_BLOCK: String = CTPreferences.PREF_BLOCK
        private const val STR_BLOCK_TOOLTIP = "block.tooltip"
        private const val STR_ICON_STYLE: String = CTPreferences.PREF_ICON_STYLE
        private const val STR_SOUND: String = CTPreferences.PREF_SOUND
        private const val STR_SOUND_TOOLTIP = "sound.tooltip"
        private const val STR_ICON_STYLE_TOOLTIP = "icon-style.tooltip"
        private const val STR_SUPPORTED_LOCALES = "supported-locales"
        private const val STR_SUPPORTED_LOCALES_TOOLTIP = "supported-locales.tooltip"
        private const val STR_TITLE = "preferences-title"
        private const val STR_TRANSPARENCY: String = CTPreferences.PREF_TRANSPARENCY
        private const val STR_TRANSPARENCY_LEVEL: String = CTPreferences.PREF_TRANSPARENCY_LEVEL
        private const val STR_TRANSPARENCY_LEVEL_TOOLTIP = "transparency-level.tooltip"
        private const val STR_TRANSPARENCY_TOOLTIP = "transparency.tooltip"

        @Serial
        private val serialVersionUID = 4767295798528273381L
        private fun createTransparencyLevelControl(
            layoutBuilder: CTLayoutBuilder,
            property: CTProperty<Int>
        ): JSlider {
            val maxOpacity: Int = CTPreferences.OPACITY_PERCENT
            val control = layoutBuilder.createSlider(property, 0, maxOpacity)
            control.extent = maxOpacity / 10
            control.paintLabels = true
            control.paintTicks = true
            control.majorTickSpacing = maxOpacity / 5
            control.minorTickSpacing = maxOpacity / 10
            return control
        }
    }
}
