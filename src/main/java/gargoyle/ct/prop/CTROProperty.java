package gargoyle.ct.prop;

public interface CTROProperty<T> {

    T get();

    Class<T> type();
}
