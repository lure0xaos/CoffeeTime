package gargoyle.ct.ui.impl.control;

import javax.swing.*;
import java.awt.*;

public class CTShowingFrame extends JFrame {
    private static final long serialVersionUID = 6336577268777238958L;

    public CTShowingFrame() throws HeadlessException {
    }

    @Override
    public boolean isShowing() {
        return true;
    }
}
