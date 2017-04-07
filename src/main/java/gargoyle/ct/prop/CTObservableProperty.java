package gargoyle.ct.prop;

import gargoyle.ct.pref.PropertyChangeListener;

public interface CTObservableProperty {
    void addPropertyChangeListener(PropertyChangeListener pcl);

    void removePropertyChangeListener(PropertyChangeListener pcl);
}
