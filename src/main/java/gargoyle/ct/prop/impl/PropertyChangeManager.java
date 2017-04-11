package gargoyle.ct.prop.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.PropertyChangeEvent;
import gargoyle.ct.pref.PropertyChangeListener;
import gargoyle.ct.prop.CTProperty;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PropertyChangeManager {
    private static final String MSG_ERROR_INVOKING_LISTENER = "Error invoking PropertyChangeListener#propertyChange";
    private static final String STR_PROPERTY_CHANGE_LISTENER = "PropertyChangeListener#propertyChange";
    private static PropertyChangeManager instance;
    private final Map<CTProperty, List<PropertyChangeListener>> listeners = new ConcurrentHashMap<>();

    public static synchronized PropertyChangeManager getInstance() {
        if (instance == null) {
            instance = new PropertyChangeManager();
        }
        return instance;
    }

    public <T> void addPropertyChangeListener(CTProperty<T> property, PropertyChangeListener listener) {
        List<PropertyChangeListener> list;
        if (listeners.containsKey(property)) {
            list = listeners.get(property);
        } else {
            list = new CopyOnWriteArrayList<>();
            listeners.put(property, list);
        }
        list.add(listener);
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

    public <T> void removePropertyChangeListener(CTProperty<T> property, PropertyChangeListener listener) {
        if (listeners.containsKey(property)) listeners.get(property).add(listener);
    }

    public void removePropertyChangeListeners() {
        listeners.clear();
    }
}
