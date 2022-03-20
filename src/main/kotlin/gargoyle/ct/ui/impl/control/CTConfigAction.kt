package gargoyle.ct.ui.impl.control

import gargoyle.ct.config.CTConfig
import gargoyle.ct.ui.impl.CTControl
import java.awt.event.ActionEvent

class CTConfigAction(private val control: CTControl, val config: CTConfig) : CTAction() {
    init {
        val text = config.name
        this.text = text
        toolTipText = text
    }

    override fun actionPerformed(e: ActionEvent) {
        control.arm(config)
    }
}
