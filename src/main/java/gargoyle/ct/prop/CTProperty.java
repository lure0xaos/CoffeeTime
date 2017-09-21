package gargoyle.ct.prop;

public interface CTProperty<T> extends CTReadOnlyProperty<T> {
    void set(T value);
}
