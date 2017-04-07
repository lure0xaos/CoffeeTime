package gargoyle.ct.prop.impl;

import gargoyle.ct.pref.PropertyChangeEvent;
import gargoyle.ct.pref.PropertyChangeListener;
import gargoyle.ct.prop.CTProperty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public enum PropertyChangeManager {
    INSTANCE;
    private final transient Map<CTProperty, List<PropertyChangeListener>> listeners = new HashMap<>();

    public static PropertyChangeManager getInstance() {
        return INSTANCE;
    }

    public <T> void addPropertyChangeListener(CTProperty<T> property, PropertyChangeListener pcl) {
        LinkedList<PropertyChangeListener> list;
        if (listeners.containsKey(property)) {
            list = (LinkedList<PropertyChangeListener>) listeners.get(property);
        } else {
            list = new LinkedList<>();
            listeners.put(property, list);
        }
        list.add(pcl);
    }

    public <T> void firePropertyChange(CTProperty<T> property, PropertyChangeEvent<T> event) {
        if (listeners.containsKey(property)) {
            List<PropertyChangeListener> listeners = this.listeners.get(property);
            for (PropertyChangeListener listener : listeners) {
                listener.propertyChange(event);
            }
        }
    }

    public <T> void removePropertyChangeListener(CTProperty<T> property, PropertyChangeListener pcl) {
        if (listeners.containsKey(property)) listeners.get(property).add(pcl);
    }

    public void removePropertyChangeListeners() {
        listeners.clear();
    }
}
