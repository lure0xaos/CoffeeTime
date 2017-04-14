package gargoyle.ct.ui.util;

import javax.swing.JComponent;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public final class CTDragHelper {

    private CTDragHelper() {
    }

    public static void makeDraggable(JComponent comp, int snap) {
        Point mouseDownLocation = new Point();
        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                mouseDownLocation.setLocation(event.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                mouseDownLocation.setLocation(0, 0);
            }
        });
        comp.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                Point currentLocation = event.getLocationOnScreen();
                Point point = new Point(currentLocation.x - mouseDownLocation.x,
                                        currentLocation.y - mouseDownLocation.y);
                Window win = SwingUtilities.getWindowAncestor(comp);
                snap(point, win.getSize(), snap);
                win.setLocation(point);
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

    public static void makeDraggable(JWindow win, int snap) {
        Point mouseDownLocation = new Point();
        win.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                mouseDownLocation.setLocation(event.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                mouseDownLocation.setLocation(0, 0);
            }
        });
        win.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                Point currentLocation = event.getLocationOnScreen();
                Point point = new Point(currentLocation.x - mouseDownLocation.x,
                                        currentLocation.y - mouseDownLocation.y);
                snap(point, win.getSize(), snap);
                win.setLocation(point);
            }
        });
    }
}
