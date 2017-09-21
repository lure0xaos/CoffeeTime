package gargoyle.ct.ui.util.render

import javax.swing.ListCellRenderer

class ToStringListCellRenderer<E : Any>(originalRenderer: ListCellRenderer<E>) :
    FunctionalListCellRenderer<E>(originalRenderer, { it.toString() })
