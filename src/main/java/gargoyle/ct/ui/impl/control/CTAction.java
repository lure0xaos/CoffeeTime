package gargoyle.ct.ui.impl.control;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public abstract class CTAction implements Action {
    private final Map<String, Object> values = new HashMap<>();
    private boolean enabled = true;

    protected CTAction() {
    }

    public CTAction(String text) {
        setText(text);
        setToolTipText(text);
    }

    public CTAction(String text, Icon icon) {
        setText(text);
        setToolTipText(text);
        setIcon(icon);
    }

    public CTAction(String text, String tooltipText, Icon icon) {
        setText(text);
        setToolTipText(tooltipText);
        setIcon(icon);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public CTAction(String text, String tooltipText) {
        setText(text);
        setToolTipText(tooltipText);
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

    protected final String getText() {
        return String.valueOf(getValue(Action.NAME));
    }
    @Override
    public Object getValue(String key) {
        return values.get(key);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void putValue(String key, Object value) {
        values.put(key, value);
    }

    protected final void setText(String text) {
        putValue(Action.NAME, text);
    }

    protected final String getToolTipText() {
        return String.valueOf(getValue(Action.SHORT_DESCRIPTION));
    }

    protected final void setToolTipText(String text) {
        putValue(Action.SHORT_DESCRIPTION, text);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }

    protected final Icon getIcon() {
        return (Icon) getValue(Action.SMALL_ICON);
    }

    protected final void setIcon(Icon icon) {
        putValue(Action.SMALL_ICON, icon);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }












}
