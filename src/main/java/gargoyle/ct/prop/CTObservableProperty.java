package gargoyle.ct.prop;

import gargoyle.ct.pref.PropertyChangeListener;

import java.util.function.Function;

public interface CTObservableProperty<T> extends CTProperty<T> {
    void addPropertyChangeListener(PropertyChangeListener<T> listener);

    <T2> void bind(CTProperty<T2> property, Function<T, T2> mapper);

    void bind(CTProperty<T> property);

    <T2> void bindBi(CTObservableProperty<T2> property, Function<T, T2> mapper, Function<T2, T> mapper2);

    void bindBi(CTObservableProperty<T> property);

    void removePropertyChangeListener(PropertyChangeListener<T> listener);
}
