package gargoyle.ct.ui.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.log.Log;
import gargoyle.ct.messages.impl.CTMessages;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class CTNewConfigDialog {
    private static final String LOC_NEW_CONFIG = "messages/new_config";
    private static final String STR_CANCEL = "cancel";
    private static final String STR_CONFIG_PATTERN = "##U/##U/##U";
    private static final String STR_NEW_CONFIG_TOOLTIP = "new-config-field.tooltip";
    private static final String STR_OK = "ok";
    private static final String STR_TITLE = "title";
    private final CTMessages messages;

    public CTNewConfigDialog() {
        messages = new CTMessages(LOC_NEW_CONFIG);
    }

    public CTConfig showConfigDialog(Component owner) {
        while (true) {
            try {
                JFormattedTextField field = new JFormattedTextField(new MaskFormatter(STR_CONFIG_PATTERN));
                field.setToolTipText(messages.getMessage(STR_NEW_CONFIG_TOOLTIP));
                int result = JOptionPane.showOptionDialog(owner, field, messages.getMessage(STR_TITLE),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        new Object[]{messages.getMessage(STR_OK), messages.getMessage(STR_CANCEL)}, null);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        return CTConfig.parse(String.valueOf(field.getValue()));
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
