package gargoyle.ct.cmd.impl;

import gargoyle.ct.prop.CTReadOnlyProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class CTBaseCmdImpl extends CTAnyCmdImpl {
    private final Map<String, CTReadOnlyProperty<?>> properties = new HashMap<>();

    public CTBaseCmdImpl(String[] args) {
        super(args);
    }

    protected final <T> void addProperty(CTReadOnlyProperty<T> property) {
        properties.put(property.name(), property);
    }

    //    @Override
    @SuppressWarnings("unchecked")
    public final <T> CTReadOnlyProperty<T> getProperty(String name) {
        return (CTReadOnlyProperty<T>) properties.get(name);
    }

    //    @Override
    @SuppressWarnings("unchecked")
    public final <E extends Enum<E>> CTReadOnlyProperty<E> getProperty(Class<E> type) {
        return (CTReadOnlyProperty<E>) properties.get(type.getSimpleName());
    }

    //    @Override
    public final Set<String> getPropertyNames() {
        return properties.keySet();
    }

    //    @Override
    public final boolean hasProperty(String name) {
        return properties.containsKey(name);
    }
}
