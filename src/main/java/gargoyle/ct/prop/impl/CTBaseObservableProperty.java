package gargoyle.ct.prop.impl;

import gargoyle.ct.pref.CTPropertyChangeEvent;
import gargoyle.ct.pref.CTPropertyChangeListener;
import gargoyle.ct.prop.CTObservableProperty;
import gargoyle.ct.prop.CTProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

public abstract class CTBaseObservableProperty<T> extends CTBaseProperty<T> implements CTObservableProperty<T> {
    protected CTBaseObservableProperty(Class<T> type, String name) {
        super(type, name);
    }

    @Nullable
    protected Thread firePropertyChange(T value, T oldValue) {
        if (Objects.equals(oldValue, value)) {
            return null;
        }
        return CTPropertyChangeManager.getInstance()
                .firePropertyChange(this,
                        new CTPropertyChangeEvent<>(this, name(), oldValue, value));
    }

    @Override
    public <T2> void bindBi(@NotNull CTObservableProperty<T2> property, @NotNull Function<T, T2> mapper, Function<T2, T> mapper2) {
        bind(property, mapper);
        property.bind(this, mapper2);
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public <T2> void bind(@NotNull CTProperty<T2> property, @NotNull Function<T, T2> mapper) {
        T myValue = get();
        T2 otherValue = property.get();
        T2 newOtherValue = mapper.apply(myValue);
        if (!Objects.equals(otherValue, newOtherValue)) {
            property.set(newOtherValue);
        }
        addPropertyChangeListener(new CTPropertyChangeListener<T>() {
            @Override
            public void onPropertyChange(@NotNull CTPropertyChangeEvent<T> event) {
                onLateBind(property, mapper, event.getNewValue());
            }
        });
    }

    <T2> void onLateBind(@NotNull CTProperty<T2> property, @NotNull Function<T, T2> mapper, T newValue) {
        property.set(mapper.apply(newValue));
    }

    @Override
    public void bindBi(@NotNull CTObservableProperty<T> property) {
        bind(property);
        property.bind(this);
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public void bind(@NotNull CTProperty<T> property) {
        T myValue = get();
        T otherValue = property.get();
        if (!Objects.equals(myValue, otherValue)) {
            onLateBind(property, myValue);
        }
        addPropertyChangeListener(new CTPropertyChangeListener<T>() {
            @Override
            public void onPropertyChange(@NotNull CTPropertyChangeEvent<T> event) {
                onLateBind(property, event.getNewValue());
            }
        });
    }

    void onLateBind(@NotNull CTProperty<T> property, T newValue) {
        property.set(newValue);
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
