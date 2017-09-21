package gargoyle.ct.util.cmd.impl

import gargoyle.ct.util.args.impl.CTArgsImpl
import gargoyle.ct.util.cmd.CTCmd
import gargoyle.ct.util.log.Log
import gargoyle.ct.util.util.CTTimeUtil


class CTCmdImpl(args: Array<String>) : CTArgsImpl(args), CTCmd {
    override val fakeTime: Long
        get() = if (has(0)) {
            try {
                CTTimeUtil.parseHHMMSS(getString(0))
            } catch (ex: NumberFormatException) {
                Log.info("fake time not set")
                0
            }
        } else 0

    override val isDebug: Boolean
        get() = size() == 1 || size() > 1 && getBoolean(1)

}
