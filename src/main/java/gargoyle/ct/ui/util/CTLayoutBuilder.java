package gargoyle.ct.ui.util;

import gargoyle.ct.messages.LocaleProvider;
import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.MessageProviderEx;
import gargoyle.ct.pref.CTPreferences.SUPPORTED_LOCALES;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;
import gargoyle.ct.prop.CTNumberProperty;
import gargoyle.ct.prop.CTObservableProperty;
import gargoyle.ct.prop.CTProperty;
import gargoyle.ct.ui.impl.CTLocalizableLabel;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

public class CTLayoutBuilder {

    private static final int GAP = 5;
    private final Container          pane;
    private final GridBagLayout      layout;
    private final GridBagConstraints controlConstraints;
    private final GridBagConstraints labelConstraints;

    public CTLayoutBuilder(Container pane) {
        layout = new GridBagLayout();
        pane.setLayout(layout);
        this.pane = pane;
        labelConstraints = createConstraints(0.0, 1);
        controlConstraints = createConstraints(1.0, GridBagConstraints.REMAINDER);
    }

    private static GridBagConstraints createConstraints(double widthx, int gridwidth) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.weightx = widthx;
        constraints.gridwidth = gridwidth;
        constraints.insets = new Insets(GAP, GAP, GAP, GAP);
        return constraints;
    }

    public void addLabeledControl(JLabel label, JComponent control) {
        layout.setConstraints(label, labelConstraints);
        pane.add(label);
        layout.setConstraints(control, controlConstraints);
        pane.add(control);
    }

    public JCheckBox createCheckBox(CTProperty<Boolean> property) {
        JCheckBox control = new JCheckBox();
        control.setSelected(property.get());
        control.addActionListener(event -> property.set(control.isSelected()));
        return control;
    }

    public JToggleButton createToggleButton(CTProperty<Boolean> property,
                                            CTObservableProperty<SUPPORTED_LOCALES> localeProperty) {
        JToggleButton control = new JToggleButton();
        control.setSelected(property.get());
        Locale initialLocale = localeProperty.get().getLocale();
        control.setText(control.isSelected() ? getYesString(initialLocale) : getNoString(initialLocale));
        control.addActionListener(event -> {
            Locale currentLocale = localeProperty.get().getLocale();
            control.setText(control.isSelected() ? getYesString(currentLocale) : getNoString(currentLocale));
            property.set(control.isSelected());
        });
        return control;
    }

    private static String getNoString(Locale locale) {
        return UIManager.getString("OptionPane.noButtonText", locale);
    }

    private static String getYesString(Locale locale) {
        return UIManager.getString("OptionPane.yesButtonText", locale);
    }

    public JSpinner createSpinner(CTProperty<Integer> property, Integer min, Integer max) {
        JSpinner control = new JSpinner(new SpinnerNumberModel());
        control.setValue(property.get());
        control.addChangeListener(event -> property.set(minmax(min,
                                                               max,
                                                               Integer.valueOf(String.valueOf(control.getValue())))));
        return control;
    }

    private static Integer minmax(Integer min, Integer max, Integer value) {
        return Math.max(min, Math.min(max, value));
    }

    public <T extends Number> JSpinner createSpinner(Class<T> type, CTProperty<T> property, T min, T max) {
        JSpinner control = new JSpinner(new SpinnerNumberModel());
        control.setValue(property.get().intValue());
        control.addChangeListener(event -> property.set(fromInt(type,
                                                                minmax(min, max, toInt((Number) control.getValue())))));
        return control;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Number> T fromInt(Class<T> type, int value) {
        try {
            return (T) type.getMethod("valueOf", String.class).invoke(null, String.valueOf(value));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static <T extends Number> int minmax(T min, T max, T value) {
        return Math.max(toInt(min), Math.min(toInt(max), toInt(value)));
    }

    private static <T extends Number> int toInt(T value) {
        return Integer.parseInt(String.valueOf(value));
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> JSpinner createSpinner(Class<T> type, CTProperty<T> property, boolean allowNull) {
        T[]      enumConstants = type.getEnumConstants();
        JSpinner control;
        if (allowNull) {
            //noinspection UseOfObsoleteCollectionType
            Vector<T> list = new Vector<>(Arrays.asList(enumConstants));
            list.add(0, null);
            control = new JSpinner(new SpinnerListModel(list));
        } else {
            control = new JSpinner(new SpinnerListModel(enumConstants));
        }
        control.setValue(property.get());
        control.addChangeListener(event -> property.set((T) control.getValue()));
        return control;
    }

    public JSlider createSlider(CTProperty<Integer> property, Integer min, Integer max) {
        JSlider control = new JSlider(toInt(min), toInt(max));
        control.setValue(property.get());
        control.addChangeListener(event -> property.set(minmax(min, max, control.getValue())));
        return control;
    }

    public <T extends Enum<T>> JSlider createSlider(Class<T> type, CTProperty<T> property, boolean allowNull) {
        T[]     enumConstants = type.getEnumConstants();
        JSlider control;
        if (allowNull) {
            List<T> list = new ArrayList<>(Arrays.asList(enumConstants));
//            list.add(0, null);
            control = new JSlider(new DefaultBoundedRangeModel(toIndex(property.get()), 1, 1, list.size()));
        } else {
            control = new JSlider(new DefaultBoundedRangeModel(toIndex(property.get()),
                                                               0,
                                                               0,
                                                               enumConstants.length - 1));
        }
        control.setValue(toIndex(property.get()));
        control.addChangeListener(event -> property.set(fromIndex(type, control.getValue())));
        control.setLabelTable(getLabels(type));
        control.setSnapToTicks(true);
        control.setPaintLabels(true);
        control.setPaintTicks(true);
        control.setPaintTrack(true);
        return control;
    }

    @SuppressWarnings("UseOfPropertiesAsHashtable")
    private static <T extends Enum<T>> Properties getLabels(Class<T> type) {
        Properties properties    = new Properties();
        T[]        enumConstants = type.getEnumConstants();
        for (int i = 0; i < enumConstants.length; i++) {
            T value = enumConstants[i];
            properties.put(i, new JLabel(value.toString()));
        }
        return properties;
    }

    private static <T extends Enum<T>> int toIndex(T value) {
        return value.ordinal();
    }

    private static <T extends Enum<T>> T fromIndex(Class<T> type, int index) {
        return type.getEnumConstants()[index];
    }

    public <T extends Number> JSlider createSlider(Class<T> type, CTNumberProperty<T> property, T min, T max) {
        JSlider control = new JSlider(toInt(min), toInt(max));
        control.setValue(property.get().intValue());
        control.addChangeListener(event -> property.set(fromInt(type, minmax(min, max, control.getValue()))));
        return control;
    }

    public JTextField createTextField(CTProperty<String> property) {
        JTextField control = new JTextField();
        control.setText(property.get());
        control.addKeyListener(new PropertyKeyAdapter(property, control));
        return control;
    }

    public JTextArea createTextArea(CTProperty<String> property) {
        JTextArea control = new JTextArea();
        control.setText(property.get());
        control.addKeyListener(new PropertyKeyAdapter(property, control));
        return control;
    }

    public JLabel createLocalizableLabel(MessageProvider messages, LocaleProvider provider, String textKey,
                                         String toolTipTextKey) {
        return new CTLocalizableLabel(messages, provider, textKey, toolTipTextKey, SwingConstants.TRAILING);
    }

    public JLabel createLocalizableLabel(MessageProviderEx messages, String textKey, String toolTipTextKey) {
        return new CTLocalizableLabel(messages, messages, textKey, toolTipTextKey, SwingConstants.TRAILING);
    }

    @SuppressWarnings({"unchecked", "TypeMayBeWeakened", "SameParameterValue"})
    public <E extends Enum<E>> JComboBox<E> createComboBox(Class<E> type, CTPrefProperty<E> property,
                                                           boolean allowNull) {
        E[]          enumConstants = type.getEnumConstants();
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
        control.addActionListener(event -> property.set((E) control.getSelectedItem()));
        return control;
    }

    public void build() {
        pane.validate();
    }

    private static class PropertyKeyAdapter extends KeyAdapter {

        private final CTProperty<String> property;
        private final JTextComponent     control;

        public PropertyKeyAdapter(CTProperty<String> property, JTextComponent control) {
            this.property = property;
            this.control = control;
        }

        @Override
        public void keyTyped(KeyEvent event) {
            property.set(control.getText());
        }
    }
}
