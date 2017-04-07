package gargoyle.ct.ui;

import gargoyle.ct.pref.PropertyChangeListener;

public interface CTControlWindow extends CTWindow, CTInformer, PropertyChangeListener {
    void setTextMode(boolean textMode);

    void setToolTipText(String text);
}
