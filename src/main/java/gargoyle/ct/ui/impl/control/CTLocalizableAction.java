package gargoyle.ct.ui.impl.control;

import gargoyle.ct.messages.MessageProvider;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public abstract class CTLocalizableAction implements Action {
    private static final String ENABLED = "enabled";
    private static final String KEY_TEXT = "text-key";
    private static final String KEY_TOOLTIP = "tooltip-key";
    private final MessageProvider messages;
    private final Map<String, Object> values = new HashMap<>();

    public CTLocalizableAction(MessageProvider messages, String textKey) {
        this.messages = messages;
        setTextKey(textKey);
        setToolTipTextKey(textKey);
        setEnabled(true);
    }

    public CTLocalizableAction(MessageProvider messages, String textKey, Icon icon) {
        this.messages = messages;
        setTextKey(textKey);
        setToolTipTextKey(textKey);
        setIcon(icon);
        setEnabled(true);
    }

    public CTLocalizableAction(MessageProvider messages, String textKey, String tooltipTextKey, Icon icon) {
        this.messages = messages;
        setTextKey(textKey);
        setToolTipTextKey(tooltipTextKey);
        setIcon(icon);
        setEnabled(true);
    }

    public CTLocalizableAction(MessageProvider messages, String textKey, String tooltipTextKey) {
        this.messages = messages;
        setTextKey(textKey);
        setToolTipTextKey(tooltipTextKey);
        setEnabled(true);
    }

    public void init(AbstractButton menuItem) {
        menuItem.setAction(this);
        menuItem.setText(getText());
        menuItem.setToolTipText(getToolTipText());
        Icon icon = getIcon();
        if (icon != null) {
            menuItem.setIcon(icon);
        }
    }

    @Override
    public final boolean isEnabled() {
        return (Boolean) getValue(ENABLED);
    }

    protected final String getText() {
        return messages.getMessage(getTextKey());
    }

    protected final String getTextKey() {
        return String.valueOf(getValue(KEY_TEXT));
    }

    protected final void setTextKey(String textKey) {
        putValue(KEY_TEXT, textKey);
    }

    protected final String getToolTipText() {
        return messages.getMessage(getToolTipTextKey());
    }

    protected final String getToolTipTextKey() {
        return String.valueOf(getValue(KEY_TOOLTIP));
    }

    protected final void setToolTipTextKey(String toolTipTextKey) {
        putValue(KEY_TOOLTIP, toolTipTextKey);
    }

    @Override
    public Object getValue(String key) {
        return values.get(key);
    }

    protected final Icon getIcon() {
        return (Icon) getValue(Action.SMALL_ICON);
    }

    protected final void setIcon(Icon icon) {
        putValue(Action.SMALL_ICON, icon);
    }

    @Override
    public final void setEnabled(boolean enabled) {
        putValue(ENABLED, enabled);
    }

    @Override
    public void putValue(String key, Object value) {
        values.put(key, value);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }





    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }
}
