package gargoyle.ct.prop;

public interface CTReadOnlyProperty<T> {

    T get();

    String name();

    Class<T> type();
}
