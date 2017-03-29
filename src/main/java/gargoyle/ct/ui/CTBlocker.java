package gargoyle.ct.ui;

import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.util.CTTimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CTBlocker extends JWindow implements CTTaskUpdatable {

    private static final float ALIGNMENT_CENTER = .5f;

    private static final int DELAY = 3;

    private static final int FONT_SCALING = 30;

    private static final String MSG_BLOCKED = "blocked_w";

    private static final String MSG_WARN = "warn_w";

    private static final int PERIOD = 60;

    private static final long serialVersionUID = 1L;
    private final transient MouseListener disposer = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            setVisible(false);
            dispose();
        }
    };
    private transient MessageProvider app;
    private JLabel lblInfo;

    private JLabel lblMain;

    @SuppressWarnings("AbsoluteAlignmentInUserInterface")
    public CTBlocker(MessageProvider app) {
        init(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), app);
    }

    @SuppressWarnings("AbsoluteAlignmentInUserInterface")
    public CTBlocker(GraphicsDevice device, MessageProvider app) {
        init(device, app);
    }

    public static List<CTBlocker> forAllDevices(MessageProvider app) {
        List<CTBlocker> devices = new ArrayList<>();
        for (GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            devices.add(new CTBlocker(device, app));
        }
        return Collections.unmodifiableList(devices);
    }

    private void init(GraphicsDevice device, MessageProvider app) {
        this.app = app;
        setBounds(device.getDefaultConfiguration().getBounds());
        setAlwaysOnTop(true);
        toFront();
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(lblMain = createMainLabel(), BorderLayout.CENTER);
        container.add(lblInfo = createInfoLabel(), BorderLayout.SOUTH);
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

    @SuppressWarnings("static-method")
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

    @SuppressWarnings("static-method")
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
                setText(app.getMessage(MSG_BLOCKED,
                        CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockEnd(currentMillis)))));
            }
            if (task.isWarn(currentMillis)) {
                setVisible(CTTimeUtil.isInPeriod(TimeUnit.SECONDS, currentMillis, PERIOD, DELAY));
                setForeground(Color.GREEN);
                setText(app.getMessage(MSG_WARN,
                        CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis)))));
            }
            if (task.isSleeping(currentMillis)) {
                setVisible(false);
            }
        }
    }

    @Override
    public void setBackground(Color color) {
        lblMain.setBackground(color);
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
}
