package gargoyle.ct;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.ui.CTApp;
import gargoyle.ct.ui.impl.CTBlocker;

import java.awt.*;
import java.util.prefs.Preferences;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class CTBlockerTest {
    private CTBlockerTest() {
    }

    public static void main(String[] args) {
        CTPreferences preferences = mock(CTPreferences.class);
        when(preferences.block()).thenReturn(new CTPrefBooleanProperty(Preferences.userNodeForPackage(CTBlockerTest.class), CTPreferences.BLOCK));
        CTApp app = mock(CTApp.class);
        when(app.preferences()).thenReturn(preferences);
        for (CTBlocker blocker : CTBlocker.forAllDevices(app)) {
            blocker.debug(true);
            blocker.showText(Color.WHITE, "00:00");
        }
    }
}
