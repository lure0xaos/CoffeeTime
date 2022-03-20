package gargoyle.ct.ui.impl.control

import java.io.Serial
import javax.swing.JCheckBoxMenuItem

class CTConfigMenuItem(action: CTConfigAction?) : JCheckBoxMenuItem(action) {
    companion object {
        @Serial
        private val serialVersionUID = -2199156620390967976L
    }
}
