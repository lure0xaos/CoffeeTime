package gargoyle.ct.ui.util.render

import gargoyle.ct.util.messages.MessageProvider
import java.util.function.Function
import javax.swing.ListCellRenderer

@Suppress("UNCHECKED_CAST")
class MessageProviderListCellRenderer<E>(
    originalRenderer: ListCellRenderer<*>,
    messageProvider: MessageProvider
) : FunctionalListCellRenderer<E>(originalRenderer as ListCellRenderer<E>, Function { e: E ->
    try {
        messageProvider.getMessage(e.toString())
    } catch (e1: Exception) {
        e.toString()
    }
}) where E : Any
