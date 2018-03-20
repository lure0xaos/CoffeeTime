package gargoyle.ct.ui.impl.control;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.ui.impl.CTControl;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;

public final class CTConfigAction extends CTAction {
    @NotNull
    private final CTConfig config;
    private final CTControl control;

    public CTConfigAction(CTControl control, CTConfig config) {
        this.control = control;
        this.config = config;
        String text = config.getName();
        setText(text);
        setToolTipText(text);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        control.arm(config);
    }

    @NotNull
    public CTConfig getConfig() {
        return config;
    }
}
