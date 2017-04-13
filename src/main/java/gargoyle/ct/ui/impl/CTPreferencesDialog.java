package gargoyle.ct.ui.impl;

import gargoyle.ct.messages.MessageProviderEx;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.CTPreferences.SUPPORTED_LOCALES;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;
import gargoyle.ct.ui.CTDialog;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class CTPreferencesDialog extends JDialog implements CTDialog<Void> {
    private static final String LOC_MESSAGES = "messages.preferences";
    private static final String STR_BLOCK = CTPreferences.BLOCK;
    private static final String STR_BLOCK_TOOLTIP = "block.tooltip";
    private static final String STR_SUPPORTED_LOCALES = "supported-locales";
    private static final String STR_SUPPORTED_LOCALES_TOOLTIP = "supported-locales.tooltip";
    private static final String STR_TITLE = "title";
    private static final String STR_TRANSPARENCY = CTPreferences.TRANSPARENCY;
    private static final String STR_TRANSPARENCY_LEVEL = CTPreferences.TRANSPARENCY_LEVEL;
    private static final String STR_TRANSPARENCY_LEVEL_TOOLTIP = "transparency-level.tooltip";
    private static final String STR_TRANSPARENCY_TOOLTIP = "transparency.tooltip";
    private static final long serialVersionUID = 4767295798528273381L;

    public CTPreferencesDialog(CTPreferences preferences, Window owner) {
        super(owner, ModalityType.MODELESS);
        init(new CTMessages(LOC_MESSAGES), preferences, getContentPane());
        pack();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void init(MessageProviderEx messages, CTPreferences preferences, Container pane) {
        preferences.supportedLocales().bind(messages.locale());
        setTitle(messages.getMessage(STR_TITLE));
        pane.setLayout(new GridLayout(0, 2, 5, 3));
        addLabeledControl(pane,
                createLocalizableLabel(messages, (STR_BLOCK), (STR_BLOCK_TOOLTIP)),
                createCheckBox(preferences.block()));
        addLabeledControl(pane,
                createLocalizableLabel(messages, (STR_TRANSPARENCY), (STR_TRANSPARENCY_TOOLTIP)),
                createCheckBox(preferences.transparency()));
        addLabeledControl(pane,
                createLocalizableLabel(messages, (STR_TRANSPARENCY_LEVEL), (STR_TRANSPARENCY_LEVEL_TOOLTIP)),
                createTransparencyLevelControl(preferences.transparencyLevel()));
        addLabeledControl(pane,
                createLocalizableLabel(messages, (STR_SUPPORTED_LOCALES), (STR_SUPPORTED_LOCALES_TOOLTIP)),
                createComboBox(SUPPORTED_LOCALES.class, preferences.supportedLocales(), false));
        preferences.supportedLocales().bind(messages.locale());
    }

    private void addLabeledControl(Container pane, JLabel label, JComponent transparencyControl) {
        pane.add(label);
        pane.add(transparencyControl);
    }

    private JSlider createTransparencyLevelControl(CTPrefProperty<Integer> property) {
        JSlider control = new JSlider(0, (int) CTPreferences.OPACITY_PERCENT);
        control.setExtent(10);
        control.setPaintLabels(true);
        control.setPaintTicks(true);
        control.setMajorTickSpacing(20);
        control.setMinorTickSpacing(10);
        control.setValue(property.get());
        control.addChangeListener(e -> property.set(Math.max(100, control.getValue())));
        return control;
    }

    private JCheckBox createCheckBox(CTPrefProperty<Boolean> property) {
        JCheckBox control = new JCheckBox();
        control.setSelected(property.get());
        control.addActionListener(e -> property.set(control.isSelected()));
        return control;
    }

    private JLabel createLocalizableLabel(MessageProviderEx messages, String textKey, String toolTipTextKey) {
        return new CTLocalizableLabel(messages, messages, textKey, toolTipTextKey, SwingConstants.TRAILING);
    }

    @SuppressWarnings("unchecked")
    private <E extends Enum<E>> JComboBox<E> createComboBox(Class<E> type, CTPrefProperty<E> property, boolean allowNull) {
        E[] enumConstants = type.getEnumConstants();
        JComboBox<E> control;
        if (allowNull) {
            //noinspection UseOfObsoleteCollectionType
            Vector<E> list = new Vector<>(Arrays.asList(enumConstants));
            list.add(0, null);
            control = new JComboBox<>(list);
        } else {
            control = new JComboBox<>(enumConstants);
        }
        control.setSelectedItem(property.get());
        control.addActionListener(e -> property.set((E) control.getSelectedItem()));
        return control;
    }

    public Void showMe() {
        setVisible(true);
        setLocationRelativeTo(getOwner());
        requestFocus();
        return null;
    }
}
