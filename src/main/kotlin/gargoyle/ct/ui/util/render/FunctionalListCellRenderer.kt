package gargoyle.ct.ui.util.render

import java.awt.Component
import java.util.function.Function
import javax.swing.JList
import javax.swing.ListCellRenderer

open class FunctionalListCellRenderer<E : Any>(
    private val originalRenderer: ListCellRenderer<E>,
    private val messageProvider: Function<E, String>
) : ListCellRenderer<E> {
    @Suppress("UNCHECKED_CAST")
    override fun getListCellRendererComponent(
        list: JList<out E>,
        value: E,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val message = messageProvider.apply(value) as E
        return originalRenderer.getListCellRendererComponent(list, message, index, isSelected, cellHasFocus)
    }
}
