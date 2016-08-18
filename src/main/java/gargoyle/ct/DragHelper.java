package gargoyle.ct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DragHelper {
    public static void makeDraggable(final JComponent comp, final int snap) {
        final Point mouseDownCompCoords = new Point();
        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                mouseDownCompCoords.setLocation(e.getPoint());
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                mouseDownCompCoords.setLocation(0, 0);
            }
        });
        comp.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent e) {
                final Point currCoords = e.getLocationOnScreen();
                final Point p = new Point(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
                final Window win = SwingUtilities.getWindowAncestor(comp);
                DragHelper.snap(p, win.getSize(), snap);
                win.setLocation(p);
            }
        });
    }

    public static void makeDraggable(final JWindow win, final int snap) {
        final Point mouseDownCompCoords = new Point();
        win.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                mouseDownCompCoords.setLocation(e.getPoint());
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                mouseDownCompCoords.setLocation(0, 0);
            }
        });
        win.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent e) {
                final Point currCoords = e.getLocationOnScreen();
                final Point p = new Point(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
                DragHelper.snap(p, win.getSize(), snap);
                win.setLocation(p);
            }
        });
    }

    static void snap(final Point p, final Dimension size, final int snap) {
        final Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        if ((p.x - bounds.x) < snap) {
            p.x = bounds.x;
        }
        if ((p.y - bounds.y) < snap) {
            p.y = bounds.y;
        }
        if (((bounds.x + bounds.width) - p.x - size.width) < snap) {
            p.x = (bounds.x + bounds.width) - size.width;
        }
        if (((bounds.y + bounds.height) - p.y - size.height) < snap) {
            p.y = (bounds.y + bounds.height) - size.height;
        }
    }
}
