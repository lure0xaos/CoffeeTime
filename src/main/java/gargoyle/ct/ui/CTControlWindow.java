package gargoyle.ct.ui;

import java.util.prefs.PreferenceChangeListener;

public interface CTControlWindow extends CTWindow, PreferenceChangeListener {
    void setToolTipText(String text);
}
