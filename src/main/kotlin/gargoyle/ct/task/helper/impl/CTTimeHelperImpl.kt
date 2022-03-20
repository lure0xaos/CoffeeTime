package gargoyle.ct.task.helper.impl

import gargoyle.ct.task.helper.CTTimeHelper
import gargoyle.ct.util.CTTimeUtil

class CTTimeHelperImpl : CTTimeHelper {
    private var delta: Long = 0
    override var fakeTime: Long = 0
        set(fakeTime) {
            field = fakeTime
            delta = fakeTime - CTTimeUtil.currentTimeMillis()
        }

    override fun currentTimeMillis(): Long {
        return CTTimeUtil.currentTimeMillis() + delta
    }
}
