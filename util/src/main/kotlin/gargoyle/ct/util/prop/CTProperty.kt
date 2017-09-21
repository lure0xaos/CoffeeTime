package gargoyle.ct.util.prop

interface CTProperty<T : Any> : CTReadOnlyProperty<T> {
    fun set(value: T)
}
