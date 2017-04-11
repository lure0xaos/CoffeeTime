package gargoyle.ct.prop.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.CTPropertyChangeEvent;
import gargoyle.ct.pref.CTPropertyChangeListener;
import gargoyle.ct.prop.CTProperty;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PropertyChangeManager {
    private static final String MSG_ERROR_INVOKING_LISTENER = "Error invoking CTPropertyChangeListener#onPropertyChange";
    private static final String STR_PROPERTY_CHANGE_LISTENER = "CTPropertyChangeListener#onPropertyChange";
    private static PropertyChangeManager instance;
    private final Map<CTProperty, List<CTPropertyChangeListener>> listeners = new ConcurrentHashMap<>();

    public static synchronized PropertyChangeManager getInstance() {
        if (instance == null) {
            instance = new PropertyChangeManager();
        }
        return instance;
    }

    public <T> void addPropertyChangeListener(CTProperty<T> property, CTPropertyChangeListener listener) {
        List<CTPropertyChangeListener> list;
        if (listeners.containsKey(property)) {
            list = listeners.get(property);
        } else {
            list = new CopyOnWriteArrayList<>();
            listeners.put(property, list);
        }
        list.add(listener);
    }

    public <T> Thread firePropertyChange(CTProperty<T> property, CTPropertyChangeEvent<T> event) {
        if (listeners.containsKey(property)) {
            List<CTPropertyChangeListener> listeners = this.listeners.get(property);
            Thread thread = new Thread(() -> {
                for (CTPropertyChangeListener listener : listeners) {
                    try {
                        //noinspection unchecked
                        listener.onPropertyChange(event);
                    } catch (Exception ex) {
                        Log.error(ex, MSG_ERROR_INVOKING_LISTENER);
                    }
                }
            }, STR_PROPERTY_CHANGE_LISTENER);
            thread.start();
            return thread;
        }
        return null;
    }

    public <T> void removePropertyChangeListener(CTProperty<T> property, CTPropertyChangeListener listener) {
        if (listeners.containsKey(property)) listeners.get(property).add(listener);
    }

    public void removePropertyChangeListeners() {
        listeners.clear();
    }
}
