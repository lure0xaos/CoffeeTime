package gargoyle.ct.ui.impl.control

import javax.swing.JFrame

class CTShowingFrame : JFrame() {
    override fun isShowing(): Boolean {
        return true
    }
}
