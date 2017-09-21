package gargoyle.ct.ui.util.render;

import gargoyle.ct.messages.Described;
import gargoyle.ct.messages.MessageProvider;

import javax.swing.ListCellRenderer;

public final class MessageProviderListCellRenderer<E extends Described> extends FunctionalListCellRenderer<E> {
    public MessageProviderListCellRenderer(final ListCellRenderer originalRenderer,
                                           final MessageProvider messageProvider) {
        super(originalRenderer, e -> {
            try {
                return e == null ? "" : messageProvider.getMessage(e.getDescription());
            } catch (Exception e1) {
                return e.getDescription();
            }
        });
    }
}
