package gargoyle.ct.pref;

@FunctionalInterface
public interface PropertyChangeListener<T> {
    void propertyChange(PropertyChangeEvent<T> event);
}
