package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

/**
 * Created by Akrukovskiy on 30.03.2017.
 */
public class CTNewConfigDialog {


    public static CTConfig showConfigDialog(Component owner, String title) {
        while (true) {
            try {
                JFormattedTextField formattedTextField = new JFormattedTextField(new MaskFormatter("##U/##U/##U"));
                int result = JOptionPane.showConfirmDialog(owner,
                        formattedTextField,
                        title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.CANCEL_OPTION) {
                    return null;
                }
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        return CTConfig.parse(String.valueOf(formattedTextField.getValue()));
                    } catch (IllegalArgumentException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (ParseException ex) {
                return null;
            }
        }
    }
}



