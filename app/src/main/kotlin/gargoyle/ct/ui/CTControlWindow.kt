package gargoyle.ct.ui


interface CTControlWindow : CTWindow, CTInformer {
    fun setTextMode(textMode: Boolean)
    fun setToolTipText(text: String)
}
