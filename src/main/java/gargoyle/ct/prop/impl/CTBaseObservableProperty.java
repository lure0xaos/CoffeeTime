package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.pref.PropertyChangeEvent;
import gargoyle.ct.pref.PropertyChangeListener;
import gargoyle.ct.prop.CTObservableProperty;
import gargoyle.ct.prop.CTProperty;

import java.util.Objects;
import java.util.function.Function;

public abstract class CTBaseObservableProperty<T> extends CTBaseProperty<T> implements CTObservableProperty<T> {
    protected CTBaseObservableProperty(Converter<T> converter, String name) {
        super(converter, name);
    }

    public CTBaseObservableProperty(Converter<T> converter, String name, T def) {
        super(converter, name, def);
    }

    protected Thread firePropertyChange(T value, T oldValue) {
        if (Objects.equals(oldValue, value)) {
            return null;
        }
        return PropertyChangeManager.getInstance().firePropertyChange(this, new PropertyChangeEvent<>(this, name(), oldValue, value));
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public <T2> void bindBi(CTObservableProperty<T2> property, Function<T, T2> mapper, Function<T2, T> mapper2) {
        bind(property, mapper);
        property.bind(this, mapper2);
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public <T2> void bind(CTProperty<T2> property, Function<T, T2> mapper) {
        addPropertyChangeListener(new PropertyChangeListener<T>() {
            @Override
            public void propertyChange(PropertyChangeEvent<T> event) {
                property.set(mapper.apply(event.getNewValue()));
            }
        });
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public void bindBi(CTObservableProperty<T> property) {
        bind(property);
        property.bind(this);
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public void bind(CTProperty<T> property) {
        addPropertyChangeListener(new PropertyChangeListener<T>() {
            @Override
            public void propertyChange(PropertyChangeEvent<T> event) {
                property.set(event.getNewValue());
            }
        });
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener<T> listener) {
        PropertyChangeManager.getInstance().addPropertyChangeListener(this, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener<T> listener) {
        PropertyChangeManager.getInstance().removePropertyChangeListener(this, listener);
    }
}
