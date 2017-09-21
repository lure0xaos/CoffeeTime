package gargoyle.ct.prop;

public interface CTReadOnlyProperty<T> {
    T get();

    boolean isPresent();

    String name();

    Class<T> type();
}
