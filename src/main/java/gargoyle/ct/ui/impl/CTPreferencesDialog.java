package gargoyle.ct.ui.impl;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.ui.CTDialog;

import javax.swing.*;
import java.awt.*;

public class CTPreferencesDialog extends JDialog implements CTDialog<Void> {
    private static final String LOC_MESSAGES = "messages.preferences";
    private static final String STR_TITLE = "title";
    private static final String STR_TRANSPARENCY = "transparency";
    private static final String STR_TRANSPARENCY_LEVEL = "transparency-level";
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
                createLabel(messages.getMessage(STR_TRANSPARENCY), messages.getMessage(STR_TRANSPARENCY_TOOLTIP)),
                createTransparencyControl(preferences));
        addLabeledControl(pane,
                createLabel(messages.getMessage(STR_TRANSPARENCY_LEVEL), messages.getMessage(STR_TRANSPARENCY_LEVEL_TOOLTIP)),
                createTransparencyEnabledControl(preferences));
    }

    private void addLabeledControl(Container pane, JLabel label, JComponent transparencyControl) {
        pane.add(label);
        pane.add(transparencyControl);
    }

    private JSlider createTransparencyEnabledControl(CTPreferences preferences) {
        JSlider control = new JSlider(0, 100);
        control.setExtent(10);
        control.setPaintLabels(true);
        control.setPaintTicks(true);
        control.setMajorTickSpacing(20);
        control.setMinorTickSpacing(10);
        control.setValue((int) (preferences.getTransparencyLevel() * 100.0d));
        control.addChangeListener(e -> preferences.setTransparencyLevel(Math.max(1.0f, control.getValue()) / 100));
        return control;
    }

    private JCheckBox createTransparencyControl(CTPreferences preferences) {
        JCheckBox control = new JCheckBox();
        control.setSelected(preferences.isTransparency());
        control.addActionListener(e -> preferences.setTransparency(control.isSelected()));
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
