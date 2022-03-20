package gargoyle.ct.ui.util

import gargoyle.ct.messages.Described
import gargoyle.ct.messages.LocaleProvider
import gargoyle.ct.messages.MessageProvider
import gargoyle.ct.messages.MessageProviderEx
import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.pref.impl.prop.CTPrefProperty
import gargoyle.ct.prop.CTNumberProperty
import gargoyle.ct.prop.CTObservableProperty
import gargoyle.ct.prop.CTProperty
import gargoyle.ct.ui.impl.CTLocalizableLabel
import gargoyle.ct.ui.util.render.MessageProviderListCellRenderer
import gargoyle.ct.util.CTNumberUtil
import java.awt.Container
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.Dictionary
import java.util.Locale
import java.util.Properties
import java.util.Vector
import javax.swing.DefaultBoundedRangeModel
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JSlider
import javax.swing.JSpinner
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.JToggleButton
import javax.swing.SpinnerListModel
import javax.swing.SpinnerNumberModel
import javax.swing.SwingConstants
import javax.swing.UIManager
import javax.swing.text.JTextComponent
import kotlin.reflect.KClass

class CTLayoutBuilder(pane: Container) {
    private val controlConstraints: GridBagConstraints
    private val labelConstraints: GridBagConstraints
    private val layout: GridBagLayout = GridBagLayout()
    private val pane: Container

    init {
        pane.layout = layout
        this.pane = pane
        labelConstraints = createConstraints(0.0, 1)
        controlConstraints = createConstraints(1.0, GridBagConstraints.REMAINDER)
    }

    fun addLabeledControl(label: JLabel?, control: JComponent?) {
        layout.setConstraints(label, labelConstraints)
        pane.add(label)
        layout.setConstraints(control, controlConstraints)
        pane.add(control)
    }

    fun build() {
        pane.validate()
    }

    fun createCheckBox(property: CTProperty<Boolean>): JCheckBox {
        val control = JCheckBox()
        control.isSelected = property.get()
        control.addActionListener { property.set(control.isSelected) }
        return control
    }

    @Suppress("UNCHECKED_CAST")
    fun <E> createComboBox(
        messages: MessageProvider, type: KClass<E>, property: CTPrefProperty<E>,
        allowNull: Boolean
    ): JComboBox<E> where E : Enum<E>, E : Described? {
        val enumConstants = type.java.enumConstants
        val control: JComboBox<E> = if (allowNull) {
            val list = Vector(listOf(*enumConstants))
            list.add(0, null)
            JComboBox(list)
        } else {
            JComboBox(enumConstants)
        }
        control.selectedItem = property.get()
        control.addActionListener { property.set(control.selectedItem as E) }
        control.renderer = MessageProviderListCellRenderer(control.renderer, messages)
        return control
    }

    fun createLocalizableLabel(
        messages: MessageProvider, provider: LocaleProvider, textKey: String,
        toolTipTextKey: String
    ): JLabel {
        return CTLocalizableLabel(messages, provider, textKey, toolTipTextKey, SwingConstants.TRAILING)
    }

    fun createLocalizableLabel(messages: MessageProviderEx, textKey: String, toolTipTextKey: String): JLabel {
        return CTLocalizableLabel(messages, messages, textKey, toolTipTextKey, SwingConstants.TRAILING)
    }

    fun <T : Enum<T>> createSlider(type: KClass<T>, property: CTProperty<T>, allowNull: Boolean): JSlider {
        val enumConstants = type.java.enumConstants
        val control: JSlider = if (allowNull) {
            val list: Collection<T> = ArrayList(listOf(*enumConstants))
            //            list.add(0, null);
            JSlider(DefaultBoundedRangeModel(toIndex(property.get()), 1, 1, list.size))
        } else {
            JSlider(
                DefaultBoundedRangeModel(
                    toIndex(property.get()),
                    0,
                    0,
                    enumConstants.size - 1
                )
            )
        }
        control.value = toIndex(property.get())
        control.addChangeListener { property.set(fromIndex(type, control.value)) }
        control.labelTable = getLabels(type)
        control.snapToTicks = true
        control.paintLabels = true
        control.paintTicks = true
        control.paintTrack = true
        return control
    }

    fun createSlider(property: CTProperty<Int>, min: Int, max: Int): JSlider {
        val control = JSlider(CTNumberUtil.toInt(min), CTNumberUtil.toInt(max))
        control.value = property.get()
        control.addChangeListener { property.set(CTNumberUtil.toRange(control.value, min, max)) }
        return control
    }

    fun <T : Number> createSlider(type: KClass<T>, property: CTNumberProperty<T>, min: T, max: T): JSlider {
        val control = JSlider(CTNumberUtil.toInt(min), CTNumberUtil.toInt(max))
        control.value = property.get().toInt()
        control.addChangeListener {
            property.set(
                CTNumberUtil.fromInt(
                    type,
                    CTNumberUtil.toRange(
                        min,
                        max,
                        control.value
                    )
                )
            )
        }
        return control
    }

    fun createSpinner(property: CTProperty<Int>, min: Int, max: Int): JSpinner {
        val control = JSpinner(SpinnerNumberModel())
        control.value = property.get()
        control.addChangeListener {
            val value = control.value
            property.set(CTNumberUtil.toRange(CTNumberUtil.getInteger(value), min, max))
        }
        return control
    }

    fun <T : Number> createSpinner(type: KClass<T>, property: CTProperty<T>, min: T, max: T): JSpinner {
        val control = JSpinner(SpinnerNumberModel())
        control.value = property.get().toInt()
        control.addChangeListener {
            property.set(
                CTNumberUtil.fromInt(
                    type,
                    CTNumberUtil.toRange(
                        min, max, CTNumberUtil.toInt(control.value as Number)
                    )
                )
            )
        }
        return control
    }

    fun <T : Enum<T>> createSpinner(type: KClass<T>, property: CTProperty<T>, allowNull: Boolean): JSpinner {
        val enumConstants = type.java.enumConstants
        val control: JSpinner = if (allowNull) {
            val list = Vector(listOf(*enumConstants))
            list.add(0, null)
            JSpinner(SpinnerListModel(list))
        } else {
            JSpinner(SpinnerListModel(enumConstants))
        }
        control.value = property.get()
        control.addChangeListener {
            @Suppress("UNCHECKED_CAST")
            property.set(control.value as T)
        }
        return control
    }

    fun createTextArea(property: CTProperty<String>): JTextArea {
        val control = JTextArea()
        control.text = property.get()
        control.addKeyListener(PropertyKeyAdapter(property, control))
        return control
    }

    fun createTextField(property: CTProperty<String>): JTextField {
        val control = JTextField()
        control.text = property.get()
        control.addKeyListener(PropertyKeyAdapter(property, control))
        return control
    }

    fun createToggleButton(
        property: CTProperty<Boolean>,
        localeProperty: CTObservableProperty<SupportedLocales>
    ): JToggleButton {
        val control = JToggleButton()
        control.isSelected = property.get()
        val initialLocale = localeProperty.get().locale
        control.text = if (control.isSelected) getYesString(initialLocale) else getNoString(initialLocale)
        control.addActionListener {
            val currentLocale = localeProperty.get().locale
            control.text = if (control.isSelected) getYesString(currentLocale) else getNoString(currentLocale)
            property.set(control.isSelected)
        }
        return control
    }

    private class PropertyKeyAdapter(private val property: CTProperty<String>, private val control: JTextComponent) :
        KeyAdapter() {
        override fun keyTyped(e: KeyEvent) {
            property.set(control.text)
        }
    }

    companion object {
        private const val GAP = 5
        private fun createConstraints(weightX: Double, gridWidth: Int): GridBagConstraints {
            val constraints = GridBagConstraints()
            constraints.fill = GridBagConstraints.HORIZONTAL
            constraints.anchor = GridBagConstraints.NORTHWEST
            constraints.weightx = weightX
            constraints.gridwidth = gridWidth
            constraints.insets = Insets(GAP, GAP, GAP, GAP)
            return constraints
        }

        private fun <T : Enum<T>> getLabels(type: KClass<T>): Dictionary<Any, Any> {
            val properties = Properties()
            val enumConstants = type.java.enumConstants
            val length = enumConstants.size
            for (i in 0 until length) {
                val value = enumConstants[i]
                properties[i] = JLabel(value.toString())
            }
            return properties
        }

        private fun <T : Enum<T>?> toIndex(value: T): Int {
            return value!!.ordinal
        }

        private fun <T : Enum<T>> fromIndex(type: KClass<T>, index: Int): T {
            return type.java.enumConstants[index]
        }

        private fun getNoString(locale: Locale?): String {
            return UIManager.getString("OptionPane.noButtonText", locale)
        }

        private fun getYesString(locale: Locale?): String {
            return UIManager.getString("OptionPane.yesButtonText", locale)
        }
    }
}
