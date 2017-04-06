package gargoyle.ct;

import gargoyle.ct.ui.impl.CTBlocker;

import java.awt.*;

public final class CTBlockerTest {
    private CTBlockerTest() {
    }

    public static void main(String[] args) {
        for (CTBlocker blocker : CTBlocker.forAllDevices()) {
            blocker.debug(true);
            blocker.showText(Color.WHITE, "00:00");
        }
    }
}
