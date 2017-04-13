package gargoyle.ct.pref;

import gargoyle.ct.prop.CTProperty;

import java.text.MessageFormat;

public class CTPropertyChangeEvent<T> {
    private final String name;
    private final T newValue;
    private final T oldValue;
    private final CTProperty<T> property;

    public CTPropertyChangeEvent(CTProperty<T> property, String name, T oldValue, T newValue) {
        this.property = property;
        this.name = name;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getName() {
        return name;
    }

    public T getNewValue() {
        return newValue;
    }

    public T getOldValue() {
        return oldValue;
    }

    public CTProperty<T> getProperty() {
        return property;
    }

    @Override
    public String toString() {
        return MessageFormat.format("CTPropertyChangeEvent'{'property={0}'}'", property);
    }
}
