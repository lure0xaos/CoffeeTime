package gargoyle.ct.ui.impl.control

import java.io.Serial
import javax.swing.JMenuItem

class CTMenuItem(action: CTAction) : JMenuItem() {
    init {
        action.init(this)
    }

    companion object {
        @Serial
        private val serialVersionUID = -5435250463745762683L
    }
}
