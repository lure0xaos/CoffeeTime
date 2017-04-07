package gargoyle.ct.ui;

import java.util.prefs.PreferenceChangeListener;

public interface CTControlWindow extends CTWindow, CTInformer, PreferenceChangeListener {
    void setTextMode(boolean textMode);

    void setToolTipText(String text);
}
