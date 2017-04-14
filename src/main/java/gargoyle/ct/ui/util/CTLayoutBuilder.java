package gargoyle.ct.ui.util;

import gargoyle.ct.messages.LocaleProvider;
import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.MessageProviderEx;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;
import gargoyle.ct.prop.CTNumberProperty;
import gargoyle.ct.prop.CTProperty;
import gargoyle.ct.ui.impl.CTLocalizableLabel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
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

    public JSpinner createSpinner(CTProperty<Integer> property, Integer min, Integer max) {
        JSpinner control = new JSpinner(new SpinnerNumberModel());
        control.setValue(property.get());
        control.addChangeListener(event -> property.set(fromInt(minmax(min,
                                                                       max,
                                                                       Integer.valueOf(String.valueOf(control.getValue()))))));
        return control;
    }

    private static <T extends Number> T fromInt(int value) {
        return (T) (Integer) value;
    }

    private static Integer minmax(Integer min, Integer max, Integer value) {
        return Math.max(min, Math.min(max, value));
    }

    public <T extends Number> JSpinner createSpinner(CTNumberProperty<T> property, T min, T max) {
        JSpinner control = new JSpinner(new SpinnerNumberModel());
        control.setValue(property.get().intValue());
        control.addChangeListener(event -> property.set(fromInt(minmax(min, max, (T) control.getValue()))));
        return control;
    }

    private static <T extends Number> int minmax(T min, T max, T value) {
        return Math.max(toInt(min), Math.min(toInt(max), toInt(value)));
    }

    private static <T extends Number> int toInt(T value) {
        return (Integer) value;
    }

    public JSlider createSlider(CTProperty<Integer> property, Integer min, Integer max) {
        JSlider control = new JSlider(toInt(min), toInt(max));
        control.setValue(property.get());
        control.addChangeListener(event -> property.set(fromInt(minmax(min, max, control.getValue()))));
        return control;
    }

    public <T extends Number> JSlider createSlider(CTNumberProperty<T> property, T min, T max) {
        JSlider control = new JSlider(toInt(min), toInt(max));
        control.setValue(property.get().intValue());
        control.addChangeListener(event -> property.set(fromInt(minmax(min, max, control.getValue()))));
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
