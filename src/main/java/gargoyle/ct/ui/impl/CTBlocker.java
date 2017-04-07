package gargoyle.ct.ui.impl;

import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.ui.CTInformer;
import gargoyle.ct.ui.CTWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CTBlocker extends JWindow implements CTTaskUpdatable, CTWindow, CTInformer {
    private static final float ALIGNMENT_CENTER = 0.5f;
    private static final int FONT_SCALING = 30;
    private static final long serialVersionUID = 1L;
    private JLabel lblInfo;
    private JLabel lblMain;
    private transient CTBlockerTextProvider textProvider;

    private CTBlocker(GraphicsDevice device) {
        init(device);
    }

    private void init(GraphicsDevice device) {
        textProvider = new CTBlockerTextProvider();
        setBounds(device.getDefaultConfiguration().getBounds());
        setAlwaysOnTop(true);
        toFront();
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        lblMain = createMainLabel();
        container.add(lblMain, BorderLayout.CENTER);
        lblInfo = createInfoLabel();
        container.add(lblInfo, BorderLayout.SOUTH);
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
    }

    private JLabel createInfoLabel() {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.GRAY);
        label.setAlignmentX(1);
        int gap = 10;
        label.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / FONT_SCALING)));
        return label;
    }

    private JLabel createMainLabel() {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(ALIGNMENT_CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 10)));
        return label;
    }

    public static List<CTBlocker> forAllDevices() {
        List<CTBlocker> devices = new ArrayList<>();
        for (GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            devices.add(new CTBlocker(device));
        }
        return Collections.unmodifiableList(devices);
    }

    public static CTBlocker forDefaultDevice() {
        return new CTBlocker(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }

    public static CTBlocker forDevice(GraphicsDevice device) {
        return new CTBlocker(device);
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
        setVisible(false);
        dispose();
    }

    @Override
    public void showMe() {
        setVisible(true);
        toFront();
    }

    @Override
    public void doUpdate(CTTask task, long currentMillis) {
        lblInfo.setText(textProvider.getInfoText(task, currentMillis));
        boolean visible = textProvider.isVisible(task, currentMillis);
        setVisible(visible);
        if (visible) {
            showText(textProvider.getColor(task, currentMillis), textProvider.getBlockerText(task, currentMillis));
        }
    }

    @Override
    public void showText(Color foreground, String text) {
        setForeground(foreground);
        lblMain.setForeground(foreground);
        lblMain.setText(text);
        if (isVisible()) {
            repaint();
            toFront();
        } else {
            showMe();
        }
    }

    @Override
    public void setBackground(Color color) {
        lblMain.setBackground(color);
    }
}
