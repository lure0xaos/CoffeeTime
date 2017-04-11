package gargoyle.ct.prop;

import gargoyle.ct.pref.PropertyChangeListener;

public interface CTObservableProperty<T> {
    void addPropertyChangeListener(PropertyChangeListener<T> listener);

    void removePropertyChangeListener(PropertyChangeListener<T> listener);
}
