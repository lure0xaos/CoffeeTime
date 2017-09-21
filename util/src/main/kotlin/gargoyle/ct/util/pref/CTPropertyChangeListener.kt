package gargoyle.ct.util.pref

fun interface CTPropertyChangeListener<T : Any> {
    fun onPropertyChange(event: CTPropertyChangeEvent<T>)
}
