package gargoyle.ct.ui.impl;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.util.CTTimeUtil;

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
import java.util.concurrent.TimeUnit;

public class CTBlocker extends JWindow implements CTTaskUpdatable {
    private static final float ALIGNMENT_CENTER = 0.5f;
    private static final int DELAY = 3;
    private static final int FONT_SCALING = 30;
    private static final String LOC_MESSAGES = "blocker";
    private static final int PERIOD = 60;
    private static final String STR_BLOCKED = "blocked_w";
    private static final String STR_WARN = "warn_w";
    private static final long serialVersionUID = 1L;
    private final transient MouseListener disposer = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            setVisible(false);
            dispose();
        }
    };
    private JLabel lblInfo;
    private JLabel lblMain;
    private transient MessageProvider messages;

    public CTBlocker(MessageProvider messages) {
        init(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), messages);
    }

    private void init(GraphicsDevice device, MessageProvider messages) {
        this.messages = messages;
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

    public CTBlocker(GraphicsDevice device, MessageProvider messages) {
        init(device, messages);
    }

    public static List<CTBlocker> forAllDevices() {
        MessageProvider messages = new CTMessages(LOC_MESSAGES);
        List<CTBlocker> devices = new ArrayList<>();
        for (GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            devices.add(new CTBlocker(device, messages));
        }
        return Collections.unmodifiableList(devices);
    }

    public void debug(boolean debug) {
        setAlwaysOnTop(!debug);
        if (debug) {
            addMouseListener(disposer);
        } else {
            removeMouseListener(disposer);
        }
    }

    @Override
    public void doUpdate(CTTask task, long currentMillis) {
        lblInfo.setText(CTTimeUtil.formatHHMMSS(currentMillis));
        if (task.isReady()) {
            if (task.isBlocked(currentMillis)) {
                setVisible(true);
                setForeground(Color.WHITE);
                setText(messages.getMessage(STR_BLOCKED,
                        CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockEnd(currentMillis)))));
            }
            if (task.isWarn(currentMillis)) {
                setVisible(CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, PERIOD, DELAY));
                setForeground(Color.GREEN);
                setText(messages.getMessage(STR_WARN,
                        CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis)))));
            }
            if (task.isSleeping(currentMillis)) {
                setVisible(false);
            }
        }
    }

    @Override
    public void setForeground(Color color) {
        lblMain.setForeground(color);
    }

    public void setText(String text) {
        lblMain.setText(text);
        repaint();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        toFront();
    }

    @Override
    public void setBackground(Color color) {
        lblMain.setBackground(color);
    }
}
