package gargoyle.ct.ui;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.pref.CTPreferences;

import javax.swing.*;
import java.awt.*;

public class CTPreferencesDialog extends JDialog {
    private static final String MSG_TRANSPARENCY_ENABLED = "preferences.transparency-enabled";
    private static final String MSG_TRANSPARENCY = "preferences.transparency";
    private static final long serialVersionUID = 4767295798528273381L;


    public CTPreferencesDialog(MessageProvider messages, CTPreferences preferences, Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        init(messages, preferences, getContentPane());
        pack();
    }

    @SuppressWarnings("UnnecessaryCodeBlock")
    private void init(MessageProvider messages, CTPreferences preferences, Container pane) {
        pane.setLayout(new GridLayout(0, 2, 5, 5));
        {
            pane.add(new JLabel(messages.getMessage(MSG_TRANSPARENCY_ENABLED), SwingConstants.TRAILING));
            JCheckBox control = new JCheckBox();
            control.setSelected(preferences.isTransparencyEnabled());
            control.addActionListener(e -> preferences.setTransparencyEnabled(control.isSelected()));
            pane.add(control);
        }
        {
            pane.add(new JLabel(messages.getMessage(MSG_TRANSPARENCY), SwingConstants.TRAILING));
            JSlider control = new JSlider(0, 100);
            control.setExtent(10);
            control.setPaintLabels(true);
            control.setPaintTicks(true);
            control.setMajorTickSpacing(20);
            control.setMinorTickSpacing(10);
            control.setValue((int) (preferences.getTransparency() * 100));
            control.addChangeListener(e -> preferences.setTransparency(Math.max(1.0f, control.getValue()) / 100));
            pane.add(control);
        }
    }


}
