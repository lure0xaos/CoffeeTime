package gargoyle.ct.ui.impl

import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.impl.CTTask
import gargoyle.ct.ui.CTBlockerTextProvider
import gargoyle.ct.ui.CTInformer
import gargoyle.ct.ui.CTWindow
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment
import java.awt.event.*
import java.io.ObjectInputStream
import java.util.*
import javax.swing.JWindow

class CTBlocker private constructor(private val preferences: CTPreferences, device: GraphicsDevice) :
    JWindow(), CTTaskUpdatable, CTWindow, CTInformer {
    private val content: CTBlockerContent

    private var textProvider: CTBlockerTextProvider

    init {
        textProvider = CTBlockerTextProviderImpl(preferences)
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
        val block = preferences.block
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

    private fun readObject(`in`: ObjectInputStream) {
        `in`.defaultReadObject()
        textProvider = CTBlockerTextProviderImpl(preferences)
    }

    override fun setBackground(bgColor: Color) {
        content.background = bgColor
    }

    companion object {

        fun forAllDevices(preferences: CTPreferences): List<CTBlocker> {
            val devices: MutableList<CTBlocker> = mutableListOf()
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
