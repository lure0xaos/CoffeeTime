package gargoyle.ct.ui;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.pref.CTPreferences;

import javax.swing.*;
import java.awt.*;

public class CTPreferencesDialog extends JDialog {
    private static final String MSG_TRANSPARENCY = "transparency";
    private static final String MSG_TRANSPARENCY_TOOLTIP = "transparency.tooltip";
    private static final String MSG_TRANSPARENCY_LEVEL = "transparency-level";
    private static final String MSG_TRANSPARENCY_LEVEL_TOOLTIP = "transparency-level.tooltip";
    private static final long serialVersionUID = 4767295798528273381L;
    private static final String LOC_MESSAGES = "preferences";

    public CTPreferencesDialog(CTPreferences preferences, Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        init(new CTMessages(LOC_MESSAGES), preferences, getContentPane());
        pack();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void init(MessageProvider messages, CTPreferences preferences, Container pane) {
        pane.setLayout(new GridLayout(0, 2, 5, 5));
        {
            JLabel label = new JLabel(messages.getMessage(MSG_TRANSPARENCY), SwingConstants.TRAILING);
            label.setToolTipText(messages.getMessage(MSG_TRANSPARENCY_TOOLTIP));
            pane.add(label);
            JCheckBox control = new JCheckBox();
            control.setSelected(preferences.isTransparency());
            control.addActionListener(e -> preferences.setTransparency(control.isSelected()));
            pane.add(control);
        }
        {
            JLabel label = new JLabel(messages.getMessage(MSG_TRANSPARENCY_LEVEL), SwingConstants.TRAILING);
            label.setToolTipText(messages.getMessage(MSG_TRANSPARENCY_LEVEL_TOOLTIP));
            pane.add(label);
            JSlider control = new JSlider(0, 100);
            control.setExtent(10);
            control.setPaintLabels(true);
            control.setPaintTicks(true);
            control.setMajorTickSpacing(20);
            control.setMinorTickSpacing(10);
            control.setValue((int) (preferences.getTransparencyLevel() * 100));
            control.addChangeListener(e -> preferences.setTransparencyLevel(Math.max(1.0f, control.getValue()) / 100));
            pane.add(control);
        }
    }

    public void showMe() {
        setVisible(true);
        setLocationRelativeTo(getOwner());
        requestFocus();
    }
}
