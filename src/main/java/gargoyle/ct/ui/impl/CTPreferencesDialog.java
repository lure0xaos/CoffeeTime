package gargoyle.ct.ui.impl;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;
import gargoyle.ct.ui.CTDialog;

import javax.swing.*;
import java.awt.*;

public class CTPreferencesDialog extends JDialog implements CTDialog<Void> {
    private static final String LOC_MESSAGES = "messages.preferences";
    private static final String STR_BLOCK = CTPreferences.BLOCK;
    private static final String STR_BLOCK_TOOLTIP = "block.tooltip";
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

    private void init(MessageProvider messages, CTPreferences preferences, Container pane) {
        setTitle(messages.getMessage(STR_TITLE));
        pane.setLayout(new GridLayout(0, 2, 5, 5));
        addLabeledControl(pane,
                createLabel(messages.getMessage(STR_BLOCK), messages.getMessage(STR_BLOCK_TOOLTIP)),
                createCheckBox(preferences.block()));
        addLabeledControl(pane,
                createLabel(messages.getMessage(STR_TRANSPARENCY), messages.getMessage(STR_TRANSPARENCY_TOOLTIP)),
                createCheckBox(preferences.transparency()));
        addLabeledControl(pane,
                createLabel(messages.getMessage(STR_TRANSPARENCY_LEVEL), messages.getMessage(STR_TRANSPARENCY_LEVEL_TOOLTIP)),
                createTransparencyLevelControl(preferences.transparencyLevel()));
    }

    private void addLabeledControl(Container pane, JLabel label, JComponent transparencyControl) {
        pane.add(label);
        pane.add(transparencyControl);
    }

    private JSlider createTransparencyLevelControl(CTPrefProperty<Float> property) {
        JSlider control = new JSlider(0, 100);
        control.setExtent(10);
        control.setPaintLabels(true);
        control.setPaintTicks(true);
        control.setMajorTickSpacing(20);
        control.setMinorTickSpacing(10);
        control.setValue((int) (property.get() * 100.0d));
        control.addChangeListener(e -> property.set(Math.max(1.0f, control.getValue()) / 100));
        return control;
    }

    private JCheckBox createCheckBox(CTPrefProperty<Boolean> property) {
        JCheckBox control = new JCheckBox();
        control.setSelected(property.get());
        control.addActionListener(e -> property.set(control.isSelected()));
        return control;
    }

    private JLabel createLabel(String text, String toolTipText) {
        JLabel label = new JLabel(text, SwingConstants.TRAILING);
        label.setToolTipText(toolTipText);
        return label;
    }

    public Void showMe() {
        setVisible(true);
        setLocationRelativeTo(getOwner());
        requestFocus();
        return null;
    }
}
