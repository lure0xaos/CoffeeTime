package gargoyle.ct.ui.impl.control

import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.ui.CTControlWindow
import gargoyle.ct.ui.CTIconProvider
import gargoyle.ct.ui.impl.CTBlockerContent
import gargoyle.ct.ui.util.CTDragHelper
import gargoyle.ct.util.log.Log
import gargoyle.ct.util.prop.PropertyObservableDelegate
import gargoyle.ct.util.util.CTTimeUtil
import java.awt.*
import java.awt.event.*
import java.io.ObjectInputStream
import javax.swing.JPopupMenu
import javax.swing.JWindow
import javax.swing.ToolTipManager
import javax.swing.UIManager
import kotlin.math.roundToLong

class CTControlWindowImpl(
    owner: Frame,
    private val preferences: CTPreferences,
    iconProvider: CTIconProvider,
    menu: JPopupMenu
) : JWindow(owner), CTControlWindow {
    private val iconContent: CTIconContent
    private val textContent: CTBlockerContent
    private var iconMode = true

    @Volatile
    private var live = true

    @Volatile
    private var reshow = false
    private var draggable = false

    init {
        UIManager.getDefaults()[TOOL_TIP_MANAGER_ENABLE_TOOL_TIP_MODE] = ""
        isAlwaysOnTop = true
        val pane = contentPane
        pane.layout = BorderLayout()
        textContent = CTBlockerContent(preferences, false)
        iconContent = CTIconContent(preferences, iconProvider)
        showIconContent()
        pack()
        setComponentPopupMenu(menu)
        val screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.size
        setLocation(screenSize.width - width, screenSize.height - height)
        getOwner().location = location
        addComponentListener(OwnerUpdater(owner))
        initToolTip()
        val updater: MouseListener = object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent) {
                onMouseMoved(reshow = true, transparency = false)
            }

            override fun mouseExited(e: MouseEvent) {
                onMouseMoved(reshow = false, transparency = true)
            }
        }
        textContent.addMouseListener(updater)
        iconContent.addMouseListener(updater)

        @Suppress("UNUSED_VARIABLE")
        var transparency by PropertyObservableDelegate(preferences::transparency) { _, _, _ ->
            transparency(true)
        }

        @Suppress("UNUSED_VARIABLE")
        var transparencyLevel by PropertyObservableDelegate(preferences::transparencyLevel) { _, _, _ ->
            transparency(true)
        }
    }

    private fun initToolTip() {
        val toolTipManager = ToolTipManager.sharedInstance()
        toolTipManager.dismissDelay = 1000
        toolTipManager.initialDelay = 100
        toolTipManager.reshowDelay = 100
        toolTipManager.isEnabled = true
        toolTipManager.isLightWeightPopupEnabled = true
    }

    private fun showIconContent() {
        val pane = contentPane
        pane.remove(textContent)
        pane.add(iconContent, BorderLayout.CENTER)
        iconContent.repaint()
        transparency(true)
    }

    private fun transparency(transparent: Boolean) {
        val preferences = preferences
        try {
            val oldOpacity = (opacity.toDouble() * CTPreferences.OPACITY_PERCENT).roundToLong().toInt()
            val newOpacity =
                if (iconMode && preferences.transparency && transparent) preferences.transparencyLevel else CTPreferences.OPACITY_PERCENT
            if (oldOpacity == newOpacity) {
                return
            }
            opacity = (newOpacity / CTPreferences.OPACITY_PERCENT.toDouble()).toFloat()
        } catch (ex: UnsupportedOperationException) {
            Log.warn(ex, MSG_TRANSPARENCY_NOT_SUPPORTED)
        }
    }

    fun onMouseMoved(reshow: Boolean, transparency: Boolean) {
        this.reshow = reshow
        transparency(transparency)
    }

    private fun setComponentPopupMenu(menu: JPopupMenu) {
        textContent.componentPopupMenu = menu
        iconContent.componentPopupMenu = menu
    }

    override fun destroy() {
        isVisible = false
        if (live) {
            live = false
            dispose()
            owner.dispose()
            live = true
        }
    }

    override fun showMe() {
        isVisible = true
        if (!draggable) {
            draggable = true
            CTDragHelper.makeDraggable(this, SNAP)
        }
    }

    private fun readObject(`in`: ObjectInputStream) {
        `in`.defaultReadObject()
    }

    override fun setTextMode(textMode: Boolean) {
        val oldIconMode = iconMode
        val newIconMode = !textMode
        if (oldIconMode == newIconMode) {
            return
        }
        iconMode = newIconMode
        transparency(true)
        if (textMode && !preferences.block) {
            showTextContent()
        } else {
            showIconContent()
        }
    }

    private fun showTextContent() {
        val pane = contentPane
        pane.remove(iconContent)
        pane.add(textContent, BorderLayout.CENTER)
        textContent.repaint()
    }

    override fun setToolTipText(text: String) {
        textContent.toolTipText = text
        iconContent.toolTipText = text
        if (reshow && text.isNotEmpty()) {
            try {
                val pointerInfo = MouseInfo.getPointerInfo()
                val v = pointerInfo.device.displayMode.height - pointerInfo.location.getY()
                val delta = if (v < 100) -TOOLTIP_OFFSET else TOOLTIP_OFFSET
                if (textContent.isVisible) {
                    ToolTipManager.sharedInstance()
                        .mouseMoved(
                            MouseEvent(
                                textContent,
                                MouseEvent.MOUSE_MOVED,
                                CTTimeUtil.currentTimeMillis(),
                                0,
                                width,
                                height + delta,
                                0,
                                false
                            )
                        )
                }
                if (iconContent.isVisible) {
                    ToolTipManager.sharedInstance()
                        .mouseMoved(
                            MouseEvent(
                                iconContent,
                                MouseEvent.MOUSE_MOVED,
                                CTTimeUtil.currentTimeMillis(),
                                0,
                                width,
                                height + delta,
                                0,
                                false
                            )
                        )
                }
            } catch (ex: Exception) {
                Log.debug(ex, MSG_TOOLTIP_ERROR)
            }
        }
    }

    override fun showText(foreground: Color, text: String) {
        textContent.showText(foreground, text)
        textContent.repaint()
    }

    private class OwnerUpdater(private val owner: Frame) : ComponentAdapter() {
        override fun componentResized(e: ComponentEvent) {
            owner.size = e.component.size
        }

        override fun componentMoved(e: ComponentEvent) {
            owner.location = e.component.location
        }
    }

    companion object {
        private const val MSG_TOOLTIP_ERROR = "tooltip error"
        private const val MSG_TRANSPARENCY_NOT_SUPPORTED = "transparency not supported"
        const val SNAP: Int = 20
        private const val TOOLTIP_OFFSET = 30
        private const val TOOL_TIP_MANAGER_ENABLE_TOOL_TIP_MODE = "ToolTipManager.enableToolTipMode"
    }
}
