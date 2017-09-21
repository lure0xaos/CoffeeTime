package gargoyle.ct.ui.impl.control;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.ui.CTIconProvider;
import gargoyle.ct.util.Defend;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import java.net.URL;

public class CTIconContent extends JLabel {
    private static final long serialVersionUID = -91984016343905171L;

    public CTIconContent(CTPreferences preferences, CTIconProvider iconProvider) {
        updateIcon(iconProvider);
        preferences.iconStyle().addPropertyChangeListener(event -> updateIcon(iconProvider));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    void updateIcon(CTIconProvider iconProvider) {
        URL icon = iconProvider.getBigIcon();
        Defend.notNull(icon, "image not found");
        setIcon(new ImageIcon(icon));
    }
}
