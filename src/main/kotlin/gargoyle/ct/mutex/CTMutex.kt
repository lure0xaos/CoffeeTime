package gargoyle.ct.mutex

interface CTMutex {
    fun acquire(): Boolean
    fun release()
}
