package gargoyle.ct.ui.impl

import gargoyle.ct.log.Log
import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.impl.CTTask
import gargoyle.ct.ui.CTBlockerTextProvider
import gargoyle.ct.ui.CTInformer
import gargoyle.ct.ui.CTWindow
import gargoyle.ct.ui.impl.control.CTControlWindowImpl
import gargoyle.ct.ui.util.CTDragHelper
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Component
import java.awt.Font
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.io.IOException
import java.io.ObjectInputStream
import java.io.Serial
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import kotlin.math.min

class CTBlockerContent(@field:Transient private val preferences: CTPreferences, big: Boolean) : JPanel(),
    CTTaskUpdatable, CTWindow, CTInformer {
    private val big: Boolean
    private val lblInfo: JLabel
    private val lblMain: JLabel

    @Transient
    private var textProvider: CTBlockerTextProvider
    private var draggable = false

    init {
        textProvider = CTBlockerTextProvider(preferences)
        this.big = big
        layout = BorderLayout()
        lblMain = createMainLabel()
        lblMain.addComponentListener(ContentComponentListener(this, lblMain))
        add(lblMain, BorderLayout.CENTER)
        lblInfo = createInfoLabel()
        lblInfo.addComponentListener(ContentComponentListener(this, lblInfo))
        add(lblInfo, BorderLayout.SOUTH)
    }

    private fun createInfoLabel(): JLabel {
        val label = JLabel()
        label.isOpaque = true
        label.background = Color.BLACK
        label.foreground = Color.GRAY
        label.alignmentX = ALIGNMENT_RIGHT
        label.border = BorderFactory.createEmptyBorder(
            GAP,
            GAP,
            GAP,
            GAP
        )
        label.horizontalAlignment = SwingConstants.RIGHT
        adjust(this, label)
        return label
    }

    private fun createMainLabel(): JLabel {
        val label = JLabel()
        label.isOpaque = true
        label.background = Color.BLACK
        label.foreground = Color.WHITE
        label.alignmentX = ALIGNMENT_CENTER
        label.horizontalAlignment = SwingConstants.CENTER
        adjust(this, label)
        return label
    }

    override fun destroy() {
        isVisible = false
    }

    override fun showMe() {
        isVisible = true
        if (!draggable) {
            draggable = true
            CTDragHelper.makeDraggable(this, CTControlWindowImpl.SNAP)
        }
    }

    override fun doUpdate(task: CTTask, currentMillis: Long) {
        lblInfo.text = textProvider.getInfoText(currentMillis)
        val visible = textProvider.isVisible(task, currentMillis)
        isVisible = visible
        if (visible) {
            showText(textProvider.getColor(task, currentMillis), textProvider.getBlockerText(task, currentMillis, big))
        }
    }

    override fun showText(foreground: Color, text: String) {
        setForeground(foreground)
        lblMain.foreground = foreground
        lblMain.text = text
        adjust(this, lblMain)
        if (isVisible) {
            Log.debug(text)
            repaint()
        } else {
            showMe()
        }
    }

    @Serial
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        `in`.defaultReadObject()
        textProvider = CTBlockerTextProvider(preferences)
    }

    override fun setBackground(bg: Color) {
//        super.setBackground(color);
        if (isDisplayable)
            lblMain.background = bg
    }

    private class ContentComponentListener(private val container: CTBlockerContent, private val label: JLabel) :
        ComponentAdapter() {
        override fun componentResized(e: ComponentEvent) {
            adjust(container, label)
        }

        override fun componentShown(e: ComponentEvent) {
            adjust(container, label)
        }
    }

    companion object {
        private const val ALIGNMENT_CENTER = 0.5f
        private const val ALIGNMENT_RIGHT = 1.0f
        private const val FONT_SIZE = 12
        private const val GAP = 10
        private const val MARGIN = 1.1

        @Serial
        private val serialVersionUID = 1873262133224449177L
        fun adjust(container: Component, label: JLabel) {
            if (!container.isVisible || container.height == 0) return
            val font = Font(Font.DIALOG, Font.PLAIN, FONT_SIZE)
            label.font = font
            label.font = Font(
                Font.DIALOG,
                Font.PLAIN,
                min(
                    (FONT_SIZE * label.width /
                            (MARGIN * label.getFontMetrics(font).stringWidth(label.text))).toInt(),
                    label.height
                )
            )
        }
    }
}
