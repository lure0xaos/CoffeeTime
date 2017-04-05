package gargoyle.ct;

import gargoyle.ct.ui.CTBlocker;

import java.util.List;

public class CTBlockerTest {
    private List<CTBlocker> blockers;

    public static void main(String[] args) {
        CTBlockerTest test = new CTBlockerTest();
        test.blockers = CTBlocker.forAllDevices();
        for (CTBlocker blocker : test.blockers) {
            blocker.debug(true);
            blocker.setText("00:00");
            blocker.setVisible(true);
            blocker.toFront();
        }
    }
}
