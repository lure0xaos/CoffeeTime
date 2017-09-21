package gargoyle.ct.ui.impl.control

import javax.swing.JMenuItem

class CTMenuItem(action: CTAction) : JMenuItem() {
    init {
        action.init(this)
    }

}
