package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.pref.CTPropertyChangeEvent;
import gargoyle.ct.pref.CTPropertyChangeListener;
import gargoyle.ct.prop.CTObservableProperty;
import gargoyle.ct.prop.CTProperty;

import java.util.Objects;
import java.util.function.Function;

public abstract class CTBaseObservableProperty<T> extends CTBaseProperty<T> implements CTObservableProperty<T> {
    protected CTBaseObservableProperty(Converter<T> converter, String name) {
        super(converter, name);
    }

    protected CTBaseObservableProperty(Converter<T> converter, String name, T def) {
        super(converter, name, def);
    }

    protected Thread firePropertyChange(T value, T oldValue) {
        if (Objects.equals(oldValue, value)) {
            return null;
        }
        return CTPropertyChangeManager.getInstance().firePropertyChange(this, new CTPropertyChangeEvent<>(this, name(), oldValue, value));
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
        T myValue = get();
        T2 otherValue = property.get();
        T2 newOtherValue = mapper.apply(myValue);
        if (!Objects.equals(otherValue, newOtherValue)) {
            property.set(newOtherValue);
        }
        addPropertyChangeListener(new CTPropertyChangeListener<T>() {
            @Override
            public void onPropertyChange(CTPropertyChangeEvent<T> event) {
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
        T myValue = get();
        T otherValue = property.get();
        if (!Objects.equals(myValue, otherValue)) {
            property.set(myValue);
        }
        addPropertyChangeListener(new CTPropertyChangeListener<T>() {
            @Override
            public void onPropertyChange(CTPropertyChangeEvent<T> event) {
                property.set(event.getNewValue());
            }
        });
    }

    @Override
    public void addPropertyChangeListener(CTPropertyChangeListener<T> listener) {
        CTPropertyChangeManager.getInstance().addPropertyChangeListener(this, listener);
    }

    @Override
    public void removePropertyChangeListener(CTPropertyChangeListener<T> listener) {
        CTPropertyChangeManager.getInstance().removePropertyChangeListener(this, listener);
    }
}
