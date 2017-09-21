package gargoyle.ct.ui

import gargoyle.ct.util.pref.CTPropertyChangeListener


interface CTControlWindow : CTWindow, CTInformer, CTPropertyChangeListener<Any> {
    fun setTextMode(textMode: Boolean)
    fun setToolTipText(text: String)
}
