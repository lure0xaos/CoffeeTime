package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.CTBlockerTest;
import gargoyle.ct.pref.CTPreferencesProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.prefs.Preferences;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CTPrefPropertyTest {

    private CTPreferencesProvider provider;

    @Before
    public void setUp() {
        Preferences preferences = mock(Preferences.class);
        Mockito.doAnswer(invocation -> {
            when(preferences.get(anyString(), anyString())).thenReturn(invocation.getArgumentAt(1, String.class));
            return null;
        }).when(preferences).put(anyString(), anyString());
        provider = mock(CTPreferencesProvider.class);
        when(provider.preferences()).thenReturn(Preferences.userNodeForPackage(CTBlockerTest.class));
    }

    @Test
    public void testBindBi() {
        CTPrefIntegerProperty prop1 = new CTPrefIntegerProperty(provider, "pref.prop31", 1); //NON-NLS
        CTPrefIntegerProperty prop2 = new CTPrefIntegerProperty(provider, "pref.prop32", 2); //NON-NLS
        prop1.bindBi(prop2);
        {
            Integer setValue = 3;
            prop1.set(setValue);
            Integer gotValue = prop2.get();
            assertEquals(setValue, gotValue);
        }
        Integer setValue = 4;
        prop2.set(setValue);
        Integer gotValue = prop1.get();
        assertEquals(setValue, gotValue);
    }

    @Test
    public void testBindMutual() {
        CTPrefIntegerProperty prop1 = new CTPrefIntegerProperty(provider, "pref.prop11", 1); //NON-NLS
        CTPrefIntegerProperty prop2 = new CTPrefIntegerProperty(provider, "pref.prop12", 2); //NON-NLS
        prop1.bind(prop2);
        prop2.bind(prop1);
        {
            Integer setValue = 3;
            prop1.set(setValue);
            Integer gotValue = prop2.get();
            assertEquals(setValue, gotValue);
        }
        Integer setValue = 4;
        prop2.set(setValue);
        Integer gotValue = prop1.get();
        assertEquals(setValue, gotValue);
    }

    @Test
    public void testBindPref() {
        CTPrefIntegerProperty prop1 = new CTPrefIntegerProperty(provider, "pref.prop21", 1); //NON-NLS
        CTPrefIntegerProperty prop2 = new CTPrefIntegerProperty(provider, "pref.prop22", 2); //NON-NLS
        prop1.bind(prop2);
        Integer setValue = 3;
        prop1.set(setValue);
        Integer gotValue = prop2.get();
        assertEquals(setValue, gotValue);
    }
}
