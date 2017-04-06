package gargoyle.ct;

import gargoyle.ct.ui.impl.CTBlocker;

public class CTBlockerTest {

    public static void main(String[] args) {
        for (CTBlocker blocker : CTBlocker.forAllDevices()) {
            blocker.debug(true);
            blocker.setText("00:00");
            blocker.setVisible(true);
            blocker.toFront();
        }
    }
}
