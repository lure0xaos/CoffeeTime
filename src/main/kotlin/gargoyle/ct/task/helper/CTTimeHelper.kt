package gargoyle.ct.task.helper

interface CTTimeHelper {
    fun currentTimeMillis(): Long
    var fakeTime: Long
}
