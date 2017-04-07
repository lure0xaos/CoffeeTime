package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.pref.PropertyChangeEvent;
import gargoyle.ct.pref.PropertyChangeListener;
import gargoyle.ct.prop.CTObservableProperty;

public abstract class CTSimpleProperty<T> extends CTBaseProperty<T> implements CTObservableProperty {
    private T value;

    protected CTSimpleProperty(Converter<T> converter, String name) {
        super(converter, name);
    }

    public CTSimpleProperty(Converter<T> converter, String name, T def) {
        super(converter, name, def);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        PropertyChangeManager.getInstance().addPropertyChangeListener(this, pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        PropertyChangeManager.getInstance().removePropertyChangeListener(this, pcl);
    }

    public T get(T def) {
        T value = get();
        return value == null ? def : value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        T oldValue = get();
        this.value = value;
        PropertyChangeManager.getInstance().firePropertyChange(this, new PropertyChangeEvent<>(this, name, oldValue, value));
    }
}
