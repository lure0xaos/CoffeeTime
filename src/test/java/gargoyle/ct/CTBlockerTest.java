package gargoyle.ct;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.ui.impl.CTBlocker;

import java.awt.Color;
import java.util.prefs.Preferences;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class CTBlockerTest {

    private CTBlockerTest() {
    }

    public static void main(String[] args) {
        CTPreferences         preferences = mock(CTPreferences.class);
        CTPreferencesProvider provider    = mock(CTPreferencesProvider.class);
        when(provider.preferences()).thenReturn(Preferences.userNodeForPackage(CTBlockerTest.class));
        when(preferences.block()).thenReturn(new CTPrefBooleanProperty(provider, CTPreferences.BLOCK));
        for (CTBlocker blocker : CTBlocker.forAllDevices(preferences)) {
            blocker.debug(true);
            blocker.showText(Color.WHITE, "00:00");
        }
    }
}
