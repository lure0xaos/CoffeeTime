package gargoyle.ct.util.mutex

interface CTMutex {
    fun acquire(): Boolean
    fun release()
}
