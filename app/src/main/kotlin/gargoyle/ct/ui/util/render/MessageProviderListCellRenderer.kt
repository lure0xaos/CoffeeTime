package gargoyle.ct.ui.util.render

import gargoyle.ct.util.messages.Described
import gargoyle.ct.util.messages.MessageProvider
import java.util.function.Function
import javax.swing.ListCellRenderer

@Suppress("UNCHECKED_CAST")
class MessageProviderListCellRenderer<E>(
    originalRenderer: ListCellRenderer<*>,
    messageProvider: MessageProvider
) : FunctionalListCellRenderer<E>(originalRenderer as ListCellRenderer<E>, Function { e: E ->
    try {
        messageProvider.getMessage(e.description)
    } catch (e1: RuntimeException) {
        e.description
    }
}) where E : Any, E : Described
