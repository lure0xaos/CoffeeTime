package gargoyle.ct.prop;

public interface CTProperty<T> {
    T get(T def);

    void set(T value);
}
