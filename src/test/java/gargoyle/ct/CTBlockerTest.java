package gargoyle.ct;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.ui.impl.CTBlocker;
import org.mockito.Mockito;

import java.awt.Color;
import java.util.prefs.Preferences;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class CTBlockerTest {

    private CTBlockerTest() {
    }

    public static void main(String[] args) {
        Preferences preferences = mock(Preferences.class);
        Mockito.doAnswer(invocation -> {
            when(preferences.get(anyString(), anyString())).thenReturn(invocation.getArgumentAt(1, String.class));
            return null;
        }).when(preferences).put(anyString(), anyString());
        CTPreferencesProvider provider = mock(CTPreferencesProvider.class);
//        when(provider.preferences()).thenReturn(Preferences.userNodeForPackage(CTBlockerTest.class));
        when(provider.preferences()).thenReturn(preferences);
        CTPreferences pref = mock(CTPreferences.class);
        when(pref.block()).thenReturn(new CTPrefBooleanProperty(provider, CTPreferences.BLOCK));
        for (CTBlocker blocker : CTBlocker.forAllDevices(pref)) {
            blocker.debug(true);
            blocker.showText(Color.WHITE, "00:00");
        }
    }
}
