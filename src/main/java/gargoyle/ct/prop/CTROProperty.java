package gargoyle.ct.prop;

public interface CTROProperty<T> {

    T get();

    String name();

    Class<T> type();
}
