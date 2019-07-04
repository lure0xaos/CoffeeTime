package gargoyle.ct.ui.impl.control;

import gargoyle.ct.messages.MessageProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@SuppressWarnings("ConstructorNotProtectedInAbstractClass")
public abstract class CTLocalizableAction extends CTAction {
    private static final String KEY_TEXT = "text-key";
    private static final String KEY_TOOLTIP = "tooltip-key";
    private final MessageProvider messages;

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

    @Override
    public void init(@NotNull AbstractButton menuItem) {
        super.init(menuItem);
        menuItem.setText(getLocalizedText());
        menuItem.setToolTipText(getToolTipLocalizedText());
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    protected final String getLocalizedText() {
        String textKey = getTextKey();
        return textKey == null || textKey.isEmpty() ? "" : messages.getMessage(textKey);
    }

    protected final String getTextKey() {
        return String.valueOf(getValue(KEY_TEXT));
    }

    protected final void setTextKey(String textKey) {
        putValue(KEY_TEXT, textKey);
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    protected final String getToolTipLocalizedText() {
        String toolTipTextKey = getToolTipTextKey();
        return toolTipTextKey == null || toolTipTextKey.isEmpty() ? "" : messages.getMessage(toolTipTextKey);
    }

    protected final String getToolTipTextKey() {
        return String.valueOf(getValue(KEY_TOOLTIP));
    }

    protected final void setToolTipTextKey(String toolTipTextKey) {
        putValue(KEY_TOOLTIP, toolTipTextKey);
    }
}
