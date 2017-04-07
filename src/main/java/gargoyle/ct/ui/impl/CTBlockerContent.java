package gargoyle.ct.ui.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.ui.CTBlockerTextProvider;
import gargoyle.ct.ui.CTInformer;
import gargoyle.ct.ui.CTWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.MessageFormat;

public final class CTBlockerContent extends JPanel implements CTTaskUpdatable, CTWindow, CTInformer {
    private static final float ALIGNMENT_CENTER = 0.5f;
    private static final float ALIGNMENT_RIGHT = 1.0f;
    private static final double FONT_SCALING_INFO_BIG = 30;
    private static final double FONT_SCALING_INFO_SMALL = 45;
    private static final double FONT_SCALING_MAIN_BIG = 10;
    private static final double FONT_SCALING_MAIN_SMALL = 8;
    private static final int GAP = 10;
    private static final String MSG_DEBUG_SIZE = "height={0}, fontScaling={1}, ratio={2}, big={3}, fontSize={4}";
    private static final long serialVersionUID = 1873262133224449177L;
    final boolean big;
    private final JLabel lblInfo;
    private final JLabel lblMain;
    private transient CTBlockerTextProvider textProvider = new CTBlockerTextProvider();

    public CTBlockerContent(boolean big) {
        this.big = big;
        setLayout(new BorderLayout());
        double fontScalingMain = big ? FONT_SCALING_MAIN_BIG : FONT_SCALING_MAIN_SMALL;
        lblMain = createMainLabel(big, fontScalingMain);
        lblMain.addComponentListener(new ContentComponentListener(lblMain, fontScalingMain));
        add(lblMain, BorderLayout.CENTER);
        double fontScalingInfo = big ? FONT_SCALING_INFO_BIG : FONT_SCALING_INFO_SMALL;
        lblInfo = createInfoLabel(big, fontScalingInfo);
        lblInfo.addComponentListener(new ContentComponentListener(lblInfo, fontScalingInfo));
        add(lblInfo, BorderLayout.SOUTH);
    }

    private JLabel createInfoLabel(boolean big, double fontScalingInfo) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.GRAY);
        label.setAlignmentX(ALIGNMENT_RIGHT);
        label.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        adjust(big, label, fontScalingInfo);
        return label;
    }

    private JLabel createMainLabel(boolean big, double fontScalingMain) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(ALIGNMENT_CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        adjust(big, label, fontScalingMain);
        return label;
    }

    @SuppressWarnings("WeakerAccess")
    void adjust(boolean big, JLabel label, double fontScaling) {
        if (!isVisible()) return;
        double height = big ? Toolkit.getDefaultToolkit().getScreenSize().getHeight() : getHeight();
        if (height == 0) return;
        Log.debug(MessageFormat.format(MSG_DEBUG_SIZE,
                height, fontScaling, height / fontScaling, big, (int) (height / fontScaling)));
        label.setFont(new Font(Font.DIALOG, Font.PLAIN, (int) (height / fontScaling)));
    }

    @Override
    public void destroy() {
        setVisible(false);
    }

    @Override
    public void showMe() {
        setVisible(true);
    }

    @Override
    public void doUpdate(CTTask task, long currentMillis) {
        lblInfo.setText(textProvider.getInfoText(task, currentMillis));
        boolean visible = textProvider.isVisible(task, currentMillis);
        setVisible(visible);
        if (visible) {
            showText(textProvider.getColor(task, currentMillis), textProvider.getBlockerText(task, currentMillis, big));
        }
    }

    @Override
    public void showText(Color foreground, String text) {
        setForeground(foreground);
        lblMain.setForeground(foreground);
        lblMain.setText(text);
        if (isVisible()) {
            Log.debug(text);
            repaint();
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
//        super.setBackground(color);
        if (lblMain != null) {
            lblMain.setBackground(color);
        }
    }

    private class ContentComponentListener implements ComponentListener {
        private final double fontScaling;
        private final JLabel label;

        @SuppressWarnings("WeakerAccess")
        public ContentComponentListener(JLabel label, double fontScaling) {
            this.label = label;
            this.fontScaling = fontScaling;
        }

        @Override
        public void componentResized(ComponentEvent e) {
            adjust(big, label, fontScaling);
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            adjust(big, label, fontScaling);
        }

        @Override
        public void componentShown(ComponentEvent e) {
            adjust(big, label, fontScaling);
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            adjust(big, label, fontScaling);
        }
    }
}
