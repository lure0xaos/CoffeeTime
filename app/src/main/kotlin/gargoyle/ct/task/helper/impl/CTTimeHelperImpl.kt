package gargoyle.ct.task.helper.impl

import gargoyle.ct.task.helper.CTTimeHelper
import gargoyle.ct.util.util.CTTimeUtil

class CTTimeHelperImpl : CTTimeHelper {
    private var delta: Long = 0
    override var fakeTime: Long = 0
        set(fakeTime) {
            field = fakeTime
            delta = fakeTime - CTTimeUtil.currentTimeMillis()
        }

    override val currentTimeMillis: Long
        get() = CTTimeUtil.currentTimeMillis() + delta
}
