package gargoyle.ct.pref

fun interface CTPropertyChangeListener<T : Any> {
    fun onPropertyChange(event: CTPropertyChangeEvent<T>)
}
