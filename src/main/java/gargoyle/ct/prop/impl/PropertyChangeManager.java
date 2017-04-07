package gargoyle.ct.prop.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.PropertyChangeEvent;
import gargoyle.ct.pref.PropertyChangeListener;
import gargoyle.ct.prop.CTProperty;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public enum PropertyChangeManager {
    INSTANCE;
    private static final String MSG_ERROR_INVOKING_LISTENER = "Error invoking PropertyChangeListener#propertyChange";
    private static final String STR_PROPERTY_CHANGE_LISTENER = "PropertyChangeListener#propertyChange";
    private final transient Map<CTProperty, List<PropertyChangeListener>> listeners = new ConcurrentHashMap<>();

    public static PropertyChangeManager getInstance() {
        return INSTANCE;
    }

    public <T> void addPropertyChangeListener(CTProperty<T> property, PropertyChangeListener pcl) {
        List<PropertyChangeListener> list;
        if (listeners.containsKey(property)) {
            list = listeners.get(property);
        } else {
            list = new CopyOnWriteArrayList<>();
            listeners.put(property, list);
        }
        list.add(pcl);
    }

    public <T> void firePropertyChange(CTProperty<T> property, PropertyChangeEvent<T> event) {
        if (listeners.containsKey(property)) {
            List<PropertyChangeListener> listeners = this.listeners.get(property);
            new Thread(() -> {
                for (PropertyChangeListener listener : listeners) {
                    try {
                        listener.propertyChange(event);
                    } catch (Exception ex) {
                        Log.error(ex, MSG_ERROR_INVOKING_LISTENER);
                    }
                }
            }, STR_PROPERTY_CHANGE_LISTENER).start();
        }
    }

    public <T> void removePropertyChangeListener(CTProperty<T> property, PropertyChangeListener pcl) {
        if (listeners.containsKey(property)) listeners.get(property).add(pcl);
    }

    public void removePropertyChangeListeners() {
        listeners.clear();
    }
}
