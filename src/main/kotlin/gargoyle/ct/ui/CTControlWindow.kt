package gargoyle.ct.ui

import gargoyle.ct.pref.CTPropertyChangeListener

interface CTControlWindow : CTWindow, CTInformer, CTPropertyChangeListener<Any> {
    fun setTextMode(textMode: Boolean)
    fun setToolTipText(text: String?)
}
