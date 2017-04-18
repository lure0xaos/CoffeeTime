package gargoyle.ct.ui.impl;

import gargoyle.ct.messages.MessageProviderEx;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.messages.impl.CTPreferencesLocaleProvider;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.CTPreferences.SUPPORTED_LOCALES;
import gargoyle.ct.prop.CTProperty;
import gargoyle.ct.ui.CTApp;
import gargoyle.ct.ui.CTDialog;
import gargoyle.ct.ui.util.CTLayoutBuilder;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JSlider;
import java.awt.Container;
import java.awt.Window;

public class CTPreferencesDialog extends JDialog implements CTDialog<Void> {

    private static final String LOC_MESSAGES                   = "messages.preferences";
    private static final String STR_BLOCK                      = CTPreferences.BLOCK;
    private static final String STR_BLOCK_TOOLTIP              = "block.tooltip";
    private static final String STR_SUPPORTED_LOCALES          = "supported-locales";
    private static final String STR_SUPPORTED_LOCALES_TOOLTIP  = "supported-locales.tooltip";
    private static final String STR_TITLE                      = "preferences-title";
    private static final String STR_TRANSPARENCY               = CTPreferences.TRANSPARENCY;
    private static final String STR_TRANSPARENCY_LEVEL         = CTPreferences.TRANSPARENCY_LEVEL;
    private static final String STR_TRANSPARENCY_LEVEL_TOOLTIP = "transparency-level.tooltip";
    private static final String STR_TRANSPARENCY_TOOLTIP       = "transparency.tooltip";
    private static final long   serialVersionUID               = 4767295798528273381L;

    public CTPreferencesDialog(CTApp app, Window owner) {
        super(owner, ModalityType.MODELESS);
        CTPreferences preferences = app.getPreferences();
        setIconImage(new ImageIcon(app.getIcon()).getImage());
        init(new CTMessages(new CTPreferencesLocaleProvider(preferences), LOC_MESSAGES), preferences, getContentPane());
        pack();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void init(MessageProviderEx messages, CTPreferences preferences, Container pane) {
        CTLayoutBuilder layoutBuilder = new CTLayoutBuilder(pane);
        setTitle(messages.getMessage(STR_TITLE));
        layoutBuilder.addLabeledControl(layoutBuilder.createLocalizableLabel(messages, STR_BLOCK, STR_BLOCK_TOOLTIP),
                                        layoutBuilder.createCheckBox(preferences.block()));
        layoutBuilder.addLabeledControl(layoutBuilder.createLocalizableLabel(messages,
                                                                             STR_TRANSPARENCY,
                                                                             STR_TRANSPARENCY_TOOLTIP),
                                        layoutBuilder.createCheckBox(preferences.transparency()));
        layoutBuilder.addLabeledControl(layoutBuilder.createLocalizableLabel(messages,
                                                                             STR_TRANSPARENCY_LEVEL,
                                                                             STR_TRANSPARENCY_LEVEL_TOOLTIP),
                                        createTransparencyLevelControl(layoutBuilder, preferences.transparencyLevel()));
        layoutBuilder.addLabeledControl(layoutBuilder.createLocalizableLabel(messages,
                                                                             STR_SUPPORTED_LOCALES,
                                                                             STR_SUPPORTED_LOCALES_TOOLTIP),
                                        layoutBuilder.createComboBox(SUPPORTED_LOCALES.class,
                                                                     preferences.supportedLocales(),
                                                                     false));
        layoutBuilder.build();
    }

    private static JSlider createTransparencyLevelControl(CTLayoutBuilder layoutBuilder, CTProperty<Integer> property) {
        int     maxOpacity = CTPreferences.OPACITY_PERCENT;
        JSlider control    = layoutBuilder.createSlider(property, 0, maxOpacity);
        control.setExtent(maxOpacity / 10);
        control.setPaintLabels(true);
        control.setPaintTicks(true);
        control.setMajorTickSpacing(maxOpacity / 5);
        control.setMinorTickSpacing(maxOpacity / 10);
        return control;
    }

    @Override
    public Void showMe() {
        setVisible(true);
        setLocationRelativeTo(getOwner());
        requestFocus();
        return null;
    }
}
