package gargoyle.ct.ui.util.render

import java.util.function.Function
import javax.swing.ListCellRenderer

class ToStringListCellRenderer<E : Any>(originalRenderer: ListCellRenderer<E>) :
    FunctionalListCellRenderer<E>(originalRenderer, Function { e: E? -> e?.toString() ?: "" })
