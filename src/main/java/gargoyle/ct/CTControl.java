package gargoyle.ct;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;

public class CTControl implements CTControlActions, CTTaskUpdatable {

    private static final String STR_EXIT = "exit";

    private static final String STR_UNARM = "unarm";

    private static final String STR_HELP = "help";

    private static final String URL_ICON = "/icon64.png";

    private final CTApp app;

    private final CTControlWindow controlWindow;

    private final ButtonGroup group;

    public CTControl(CTApp app) {
        this.app = app;
        group = new ButtonGroup();
        controlWindow = new CTControlWindow(CTControl.class.getResource(URL_ICON), createMenu(app.getConfigs()));
        controlWindow.setVisible(true);
    }

    private void addConfig(JPopupMenu menu, CTConfig config) {
        JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(new ConfigAction(config));
        group.add(menuItem);
        menu.add(menuItem);
    }

    private void addConfigs(JPopupMenu menu, CTConfigs configs) {
        for (CTConfig config : configs.getConfigs()) {
            addConfig(menu, config);
        }
    }

    @Override
    public void arm(CTConfig config) {
        AbstractButton item = findItem(config);
        if (item != null) {
            group.clearSelection();
            group.setSelected(item.getModel(), true);
            app.arm(config);
        }
    }

    private JPopupMenu createMenu(CTConfigs configs) {
        JPopupMenu menu = new JPopupMenu();
        addConfigs(menu, configs);
        menu.add(new JSeparator(SwingConstants.HORIZONTAL));
        menu.add(new JMenuItem(new AbstractAction(app.getMessage(STR_UNARM)) {
            private static final long serialVersionUID = -4330571111080076360L;

            @Override
            public void actionPerformed(ActionEvent e) {
                unarm();
            }
        }));
        menu.add(new JMenuItem(new AbstractAction(app.getMessage(STR_HELP)) {
            private static final long serialVersionUID = 5717750136378884217L;

            @Override
            public void actionPerformed(ActionEvent e) {
                help();
            }
        }));
        menu.add(new JMenuItem(new AbstractAction(app.getMessage(STR_EXIT)) {
            private static final long serialVersionUID = 6450213490024118820L;

            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        }));
        return menu;
    }

    @Override
    public void doUpdate(CTTask task, long currentMillis) {
        if (task.isReady()) {
            if (task.isBlocked(currentMillis)) {
                controlWindow.setToolTipText(
                    CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockEnd(currentMillis))));
            }
            if (task.isWarn(currentMillis)) {
                controlWindow.setToolTipText(
                    CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis))));
            }
            if (task.isSleeping(currentMillis)) {
                controlWindow.setToolTipText(
                    CTTimeUtil.formatMMSS(CTTimeUtil.timeRemainsTo(currentMillis, task.getBlockStart(currentMillis))));
            }
        } else {
            controlWindow.setToolTipText(CTTimeUtil.formatHHMMSS(currentMillis));
        }
    }

    @Override
    public void exit() {
        controlWindow.setVisible(false);
        controlWindow.dispose();
        app.unarm();
        app.exit();
    }

    private AbstractButton findItem(CTConfig config) {
        for (AbstractButton button : Collections.list(group.getElements())) {
            Action action = button.getAction();
            if (action instanceof ConfigAction) {
                ConfigAction configAction = (ConfigAction) action;
                CTConfig cfg = configAction.getConfig();
                if (Objects.equals(cfg, config)) {
                    return button;
                }
            }
        }
        return null;
    }

    @Override
    public CTConfigs getConfigs() {
        return null;
    }

    @Override
    public void help() {
        app.help();
    }

    @Override
    public void unarm() {
        group.clearSelection();
        app.unarm();
    }

    private static final class CTControlWindow extends JWindow {

        private static final long serialVersionUID = 1L;

        private final JLabel label;

        volatile boolean reshow;

        private volatile boolean live = true;

        public CTControlWindow(URL imageURL, JPopupMenu menu) {
            super(new JShowingFrame());
            if (imageURL == null) {
                throw new IllegalArgumentException("image not found");
            }
            UIManager.getDefaults().put("ToolTipManager.enableToolTipMode", "");
            setAlwaysOnTop(true);
            Container pane = getContentPane();
            pane.setLayout(new BorderLayout());
            label = new JLabel(new ImageIcon(imageURL));
            pane.add(label, BorderLayout.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pack();
            setComponentPopupMenu(menu);
            Dimension screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();
            setLocation(screenSize.width - getWidth(), screenSize.height - getHeight());
            int snap = 20;
            DragHelper.makeDraggable(label, snap);
            DragHelper.makeDraggable(this, snap);
            ToolTipManager.sharedInstance().setDismissDelay(1000);
            ToolTipManager.sharedInstance().setInitialDelay(100);
            ToolTipManager.sharedInstance().setReshowDelay(100);
            ToolTipManager.sharedInstance().setEnabled(true);
            ToolTipManager.sharedInstance().setLightWeightPopupEnabled(true);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    reshow = true;
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    reshow = false;
                }
            });
        }

        @Override
        public void dispose() {
            if (live) {
                live = false;
                super.dispose();
                getOwner().dispose();
                live = true;
            }
        }

        public void setComponentPopupMenu(JPopupMenu menu) {
            label.setComponentPopupMenu(menu);
        }

        public void setToolTipText(String text) {
            label.setToolTipText(text);
            if (reshow && text != null && !text.isEmpty()) {
                try {
                    ToolTipManager.sharedInstance()
                        .mouseMoved(
                            new MouseEvent(label, MouseEvent.MOUSE_MOVED, CTTimeUtil.currentTimeMillis(), 0, getWidth(),
                                getHeight(), 0, false));
                } catch (RuntimeException ex) {
                    // IGNORE
                }
            }
        }

        private static class JShowingFrame extends JFrame {

            private static final long serialVersionUID = 1L;

            public JShowingFrame() throws HeadlessException {}

            @Override
            public boolean isShowing() {
                return true;
            }
        }
    }

    private final class ConfigAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        private final CTConfig config;

        public ConfigAction(CTConfig config) {
            super(config.getName());
            this.config = config;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            arm(config);
        }

        public CTConfig getConfig() {
            return config;
        }
    }
}
