package gargoyle.ct.ui.impl;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.ui.CTBlockerTextProvider;
import gargoyle.ct.ui.CTInformer;
import gargoyle.ct.ui.CTWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CTBlocker extends JWindow implements CTTaskUpdatable, CTWindow, CTInformer {
    private static final long serialVersionUID = 4716380852101644265L;
    private final CTBlockerContent content;
    private final transient CTPreferences preferences;
    private transient CTBlockerTextProvider textProvider = new CTBlockerTextProvider();

    private CTBlocker(CTPreferences preferences, GraphicsDevice device) {
        setBounds(device.getDefaultConfiguration().getBounds());
        setAlwaysOnTop(true);
        toFront();
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        content = new CTBlockerContent(true);
        container.add(content, BorderLayout.CENTER);
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                //
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                if (isVisible()) {
                    e.getWindow().requestFocus();
                }
            }
        });
        this.preferences = preferences;
    }

    public static List<CTBlocker> forAllDevices(CTPreferences preferences) {
        List<CTBlocker> devices = new ArrayList<>();
        for (GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            devices.add(new CTBlocker(preferences, device));
        }
        return Collections.unmodifiableList(devices);
    }

    public static CTBlocker forDefaultDevice(CTPreferences preferences) {
        return new CTBlocker(preferences, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }

    public static CTBlocker forDevice(CTPreferences preferences, GraphicsDevice device) {
        return new CTBlocker(preferences, device);
    }

    public void debug(boolean debug) {
        setAlwaysOnTop(!debug);
        MouseListener l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                destroy();
            }
        };
        if (debug) {
            addMouseListener(l);
        } else {
            removeMouseListener(l);
        }
    }

    @Override
    public void destroy() {
        content.destroy();
        setVisible(false);
        dispose();
    }

    @Override
    public void showMe() {
        content.showMe();
        setVisible(true);
        toFront();
    }

    @Override
    public void doUpdate(CTTask task, long currentMillis) {
        content.doUpdate(task, currentMillis);
        boolean block = preferences.block().get();
        boolean visible = block && textProvider.isVisible(task, currentMillis);
        setVisible(visible);
        content.setVisible(visible);
        if (visible) {
            showText(textProvider.getColor(task, currentMillis), textProvider.getBlockerText(task, currentMillis, true));
        }
    }

    @Override
    public void showText(Color foreground, String text) {
        content.showText(foreground, text);
        setForeground(foreground);
        if (isVisible()) {
            repaint();
            toFront();
        } else {
            showMe();
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        textProvider = new CTBlockerTextProvider();
    }

    @Override
    public void setBackground(Color color) {
        content.setBackground(color);
    }
}
