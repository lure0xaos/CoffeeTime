package gargoyle.ct.ui.impl.control

import java.io.Serial
import javax.swing.JFrame

class CTShowingFrame : JFrame() {
    override fun isShowing(): Boolean {
        return true
    }

    companion object {
        @Serial
        private val serialVersionUID = 6336577268777238958L
    }
}
