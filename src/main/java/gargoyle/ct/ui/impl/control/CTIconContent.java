package gargoyle.ct.ui.impl.control;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import java.net.URL;

public class CTIconContent extends JLabel {

    private static final long serialVersionUID = -91984016343905171L;

    public CTIconContent(URL imageURL) {
        super(new ImageIcon(check(imageURL)));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private static URL check(URL imageURL) {
        if (imageURL == null) {
            throw new IllegalArgumentException("image not found");
        }
        return imageURL;
    }
}
