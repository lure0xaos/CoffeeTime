package gargoyle.ct.ui.util;

import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public final class CTDragHelper {

    private CTDragHelper() {
    }

    public static void makeDraggable(Component component, int snap) {
        makeDraggable0(component, snap, SwingUtilities.getWindowAncestor(component));
    }

    private static void makeDraggable0(Component component, int snap, Component window) {
        Point mouseDownLocation = new Point();
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                mouseDownLocation.setLocation(event.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                mouseDownLocation.setLocation(0, 0);
            }
        });
        component.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                Point currentLocation = event.getLocationOnScreen();
                Point point = new Point(currentLocation.x - mouseDownLocation.x,
                                        currentLocation.y - mouseDownLocation.y);
                snap(point, window.getSize(), snap);
                window.setLocation(point);
            }
        });
    }

    private static void snap(Point p, Dimension size, int snap) {
        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        if (p.x - bounds.x < snap) {
            p.x = bounds.x;
        }
        if (p.y - bounds.y < snap) {
            p.y = bounds.y;
        }
        if (bounds.x + bounds.width - p.x - size.width < snap) {
            p.x = bounds.x + bounds.width - size.width;
        }
        if (bounds.y + bounds.height - p.y - size.height < snap) {
            p.y = bounds.y + bounds.height - size.height;
        }
    }
}
