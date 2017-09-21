package gargoyle.ct.ui.util

import gargoyle.ct.util.util.CTNumberUtil
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.SwingUtilities

object CTDragHelper {
    fun makeDraggable(component: Component, snap: Int) {
        makeDraggable0(component, snap, SwingUtilities.getWindowAncestor(component))
    }

    private fun makeDraggable0(component: Component, snap: Int, window: Component) {
        val mouseDownLocation = Point()
        component.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                mouseDownLocation.location = e.point
            }

            override fun mouseReleased(e: MouseEvent) {
                mouseDownLocation.setLocation(0, 0)
            }
        })
        component.addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                val currentLocation = e.locationOnScreen
                val point = Point(
                    currentLocation.x - mouseDownLocation.x,
                    currentLocation.y - mouseDownLocation.y
                )
                snap(point, window.size, snap)
                window.location = point
            }
        })
    }

    fun snap(p: Point, size: Dimension, snap: Int) {
        val bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds
        if (p.x - bounds.x < snap) {
            p.x = bounds.x
        }
        if (p.y - bounds.y < snap) {
            p.y = bounds.y
        }
        if (bounds.x + bounds.width - p.x - size.width < snap) {
            p.x = bounds.x + bounds.width - size.width
        }
        if (bounds.y + bounds.height - p.y - size.height < snap) {
            p.y = bounds.y + bounds.height - size.height
        }
    }

    fun setLocationRelativeTo(comp: Window, owner: Window?) {
        val screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.size
        comp.setLocationRelativeTo(owner)
        comp.setLocation(
            CTNumberUtil.toRange(comp.x, 0, screenSize.width - comp.width),
            CTNumberUtil.toRange(comp.y, 0, screenSize.height - comp.height)
        )
    }
}
