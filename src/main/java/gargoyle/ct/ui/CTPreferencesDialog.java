package gargoyle.ct.ui;

import gargoyle.ct.pref.CTPreferences;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CTPreferencesDialog extends JDialog {
    private final CTApp app;
    private JCheckBox chkTranparencyEnabled;
    private JSlider sldTranparency;

    public CTPreferencesDialog(CTApp app, Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        this.app = app;
        Container pane = getContentPane();
        init(pane);
        pack();
    }

    private void init(Container pane) {
        setLayout(new GridLayout(0, 2));
        pane.add(new JLabel(app.getMessage(CTPreferences.TRANSPARENCY_ENABLED)));
        chkTranparencyEnabled = new JCheckBox();
        chkTranparencyEnabled.setSelected(app.preferences().isTransparencyEnabled());
        chkTranparencyEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.preferences().setTransparencyEnabled(chkTranparencyEnabled.isSelected());
            }
        });
        pane.add(chkTranparencyEnabled);
        pane.add(new JLabel(app.getMessage(CTPreferences.TRANSPARENCY)));
        sldTranparency = new JSlider(0, 100);
        sldTranparency.setValue((int) (app.preferences().getTransparency() * 100));
        sldTranparency.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                app.preferences().setTransparency(sldTranparency.getValue() / 100f);
            }
        });
        pane.add(sldTranparency);
    }
}
