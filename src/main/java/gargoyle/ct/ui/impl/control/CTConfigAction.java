package gargoyle.ct.ui.impl.control;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.ui.impl.CTControl;

import java.awt.event.ActionEvent;

public final class CTConfigAction extends CTAction {

    private final CTConfig  config;
    private final CTControl control;

    public CTConfigAction(CTControl control, CTConfig config) {
        this.control = control;
        this.config = config;
        String text = config.getName();
        setText(text);
        setToolTipText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        control.arm(config);
    }

    public CTConfig getConfig() {
        return config;
    }
}
