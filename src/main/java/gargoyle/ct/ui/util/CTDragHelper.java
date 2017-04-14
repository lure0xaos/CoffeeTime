package gargoyle.ct.ui.util;

import javax.swing.*;
import java.awt.*;
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
            public void mousePressed(MouseEvent e) {
                mouseDownLocation.setLocation(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseDownLocation.setLocation(0, 0);
            }
        });
        comp.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentLocation = e.getLocationOnScreen();
                Point p = new Point(currentLocation.x - mouseDownLocation.x, currentLocation.y - mouseDownLocation.y);
                Window win = SwingUtilities.getWindowAncestor(comp);
                snap(p, win.getSize(), snap);
                win.setLocation(p);
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
            public void mousePressed(MouseEvent e) {
                mouseDownLocation.setLocation(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseDownLocation.setLocation(0, 0);
            }
        });
        win.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentLocation = e.getLocationOnScreen();
                Point p = new Point(currentLocation.x - mouseDownLocation.x, currentLocation.y - mouseDownLocation.y);
                snap(p, win.getSize(), snap);
                win.setLocation(p);
            }
        });
    }
}
