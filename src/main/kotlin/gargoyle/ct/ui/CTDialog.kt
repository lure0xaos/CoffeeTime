package gargoyle.ct.ui

fun interface CTDialog<R> {
    fun showMe(): R?
}
