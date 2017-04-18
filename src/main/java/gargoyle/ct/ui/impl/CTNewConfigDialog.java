package gargoyle.ct.ui.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.convert.CTUnitConverter;
import gargoyle.ct.config.convert.impl.CTConfigConverter;
import gargoyle.ct.log.Log;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.messages.impl.CTPreferencesLocaleProvider;
import gargoyle.ct.ui.CTApp;
import gargoyle.ct.ui.CTDialog;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import java.awt.Component;
import java.awt.Image;
import java.text.ParseException;

public class CTNewConfigDialog implements CTDialog<CTConfig> {

    private static final String                    LOC_NEW_CONFIG         = "messages/new_config";
    private static final String                    STR_CANCEL             = "cancel";
    private static final String                    STR_CONFIG_PATTERN     = "##U/##U/##U";
    private static final String                    STR_NEW_CONFIG_TOOLTIP = "new-config-field.tooltip";
    private static final String                    STR_OK                 = "ok";
    private static final String                    STR_TITLE              = "new-config-title";
    private static final int                       ICON_DIALOG_SIZE       = 32;
    private final        CTUnitConverter<CTConfig> configConverter        = new CTConfigConverter();
    private final CTMessages messages;
    private final Component  owner;
    private final Icon       icon;

    public CTNewConfigDialog(CTApp app, Component owner) {
        this.owner = owner;
        messages = new CTMessages(new CTPreferencesLocaleProvider(app.getPreferences()), LOC_NEW_CONFIG);
        icon = new ImageIcon(new ImageIcon(app.getIcon()).getImage()
                                                         .getScaledInstance(ICON_DIALOG_SIZE,
                                                                            ICON_DIALOG_SIZE,
                                                                            Image.SCALE_SMOOTH));
    }

    @Override
    public CTConfig showMe() {
        while (true) {
            try {
                JFormattedTextField field = new JFormattedTextField(new MaskFormatter(STR_CONFIG_PATTERN));
                field.setToolTipText(messages.getMessage(STR_NEW_CONFIG_TOOLTIP));
                int result = JOptionPane.showOptionDialog(owner,
                                                          field,
                                                          messages.getMessage(STR_TITLE),
                                                          JOptionPane.OK_CANCEL_OPTION,
                                                          JOptionPane.QUESTION_MESSAGE, icon,
                                                          new Object[]{messages.getMessage(STR_OK),
                                                                       messages.getMessage(STR_CANCEL)},
                                                          null);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        return configConverter.parse(String.valueOf(field.getValue()));
                    } catch (IllegalArgumentException ex) {
                        Log.debug(ex, ex.getMessage());
                    }
                }
                if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.NO_OPTION) {
                    return null;
                }
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
