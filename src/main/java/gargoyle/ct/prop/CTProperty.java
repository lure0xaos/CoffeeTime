package gargoyle.ct.prop;

public interface CTProperty<T> {
    T get();

    void set(T value);
}
