package gargoyle.ct.pref;

@FunctionalInterface
public interface CTPropertyChangeListener<T> {
    void onPropertyChange(CTPropertyChangeEvent<T> event);
}
