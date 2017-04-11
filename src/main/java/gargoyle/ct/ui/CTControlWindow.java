package gargoyle.ct.ui;

import gargoyle.ct.pref.CTPropertyChangeListener;

public interface CTControlWindow extends CTWindow, CTInformer, CTPropertyChangeListener {
    void setTextMode(boolean textMode);

    void setToolTipText(String text);
}
