package gargoyle.ct.ui.impl.control;

import gargoyle.ct.config.data.CTConfig;
import gargoyle.ct.ui.impl.CTControl;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

public final class CTConfigAction extends CTAction {
    private static final long serialVersionUID = 8001396484814809015L;
    private final transient CTControl control;
    private CTConfig config;

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

    public CTConfigAction clone() throws CloneNotSupportedException {
        return (CTConfigAction) super.clone();
    }

    public CTConfig getConfig() {
        return config;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        config = new CTConfig();
    }
}
