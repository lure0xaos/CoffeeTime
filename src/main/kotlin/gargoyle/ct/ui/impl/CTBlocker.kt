package gargoyle.ct.ui.impl

import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.impl.CTTask
import gargoyle.ct.ui.CTBlockerTextProvider
import gargoyle.ct.ui.CTInformer
import gargoyle.ct.ui.CTWindow
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException
import java.io.ObjectInputStream
import java.io.Serial
import java.util.Collections
import javax.swing.JWindow

class CTBlocker private constructor(@field:Transient private val preferences: CTPreferences, device: GraphicsDevice) :
    JWindow(), CTTaskUpdatable, CTWindow, CTInformer {
    private val content: CTBlockerContent

    @Transient
    private var textProvider: CTBlockerTextProvider

    init {
        textProvider = CTBlockerTextProvider(preferences)
        bounds = device.defaultConfiguration.bounds
        isAlwaysOnTop = true
        toFront()
        val container = contentPane
        container.layout = BorderLayout()
        content = CTBlockerContent(preferences, true)
        container.add(content, BorderLayout.CENTER)
        addWindowFocusListener(object : WindowAdapter() {
            override fun windowLostFocus(e: WindowEvent) {
                holdFocus(e)
            }
        })
    }

    fun holdFocus(event: WindowEvent) {
        if (isVisible) {
            event.window.requestFocus()
        }
    }

    fun debug(debug: Boolean) {
        isAlwaysOnTop = !debug
        val clickToDestroy: MouseListener = object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                destroy()
            }
        }
        removeMouseListener(clickToDestroy)
        if (debug) {
            addMouseListener(clickToDestroy)
        }
    }

    override fun destroy() {
        content.destroy()
        isVisible = false
        dispose()
    }

    override fun showMe() {
        content.showMe()
        isVisible = true
        toFront()
    }

    override fun doUpdate(task: CTTask, currentMillis: Long) {
        content.doUpdate(task, currentMillis)
        val block = preferences.block().get()
        val visible = block && textProvider.isVisible(task, currentMillis)
        isVisible = visible
        content.isVisible = visible
        if (visible) {
            showText(
                textProvider.getColor(task, currentMillis),
                textProvider.getBlockerText(task, currentMillis, true)
            )
        }
    }

    override fun showText(foreground: Color, text: String) {
        content.showText(foreground, text)
        setForeground(foreground)
        if (isVisible) {
            repaint()
            toFront()
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

    override fun setBackground(bgColor: Color) {
        content.background = bgColor
    }

    companion object {
        @Serial
        private val serialVersionUID = 4716380852101644265L

        @JvmStatic
        fun forAllDevices(preferences: CTPreferences): List<CTBlocker> {
            val devices: MutableList<CTBlocker> = ArrayList()
            for (device in GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices) {
                devices.add(CTBlocker(preferences, device))
            }
            return Collections.unmodifiableList(devices)
        }

        fun forDefaultDevice(preferences: CTPreferences): CTBlocker {
            return CTBlocker(preferences, GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice)
        }

        fun forDevice(preferences: CTPreferences, device: GraphicsDevice): CTBlocker {
            return CTBlocker(preferences, device)
        }
    }
}
