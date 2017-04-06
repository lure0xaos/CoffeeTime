package gargoyle.ct.ui.impl.control;

import javax.swing.*;

public abstract class CTAction extends AbstractAction {
    private static final long serialVersionUID = -5252756701531090882L;

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

    public CTAction(String text, String tooltipText) {
        setText(text);
        setToolTipText(tooltipText);
    }

    public CTAction clone() throws CloneNotSupportedException {
        return (CTAction) super.clone();
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

    protected final void setText(String text) {
        putValue(Action.NAME, text);
    }

    protected final String getToolTipText() {
        return String.valueOf(getValue(Action.SHORT_DESCRIPTION));
    }

    protected final void setToolTipText(String text) {
        putValue(Action.SHORT_DESCRIPTION, text);
    }

    protected final Icon getIcon() {
        return (Icon) getValue(Action.SMALL_ICON);
    }

    protected final void setIcon(Icon icon) {
        putValue(Action.SMALL_ICON, icon);
    }
}
