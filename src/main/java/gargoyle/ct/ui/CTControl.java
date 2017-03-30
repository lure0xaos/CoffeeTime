package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.helper.DragHelper;
import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.util.CTTimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;

public class CTControl implements CTControlActions, CTTaskUpdatable {

    private static final int SNAP = 20;

    private static final String STR_EXIT = "exit";

    private static final String STR_HELP = "help";

    private static final String STR_NEW_CONFIG = "new-config";

    private static final String STR_UNARM = "unarm";

    private static final String URL_ICON = "/icon64.png";

    private final CTApp app;

    private final CTControlWindow controlWindow;

    private final ButtonGroup group;

    public CTControl(CTApp app) {
        this.app = app;
        group = new ButtonGroup();
        controlWindow = new CTControlWindow(CTControl.class.getResource(URL_ICON), createMenu(app.loadConfigs(false)));
        controlWindow.setVisible(true);
    }

    private void addConfig(JPopupMenu menu, CTConfig config) {
        CTConfigMenuItem menuItem = new CTConfigMenuItem(new ConfigAction(config));
        group.add(menuItem);
        menu.insert(menuItem, group.getButtonCount() - 1);
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

    @SuppressWarnings({"CloneableClassWithoutClone", "SerializableInnerClassWithNonSerializableOuterClass"})
    private JPopupMenu createMenu(CTConfigs configs) {
        JPopupMenu menu = new JPopupMenu();
        addConfigs(menu, configs);
        menu.add(new JSeparator(SwingConstants.HORIZONTAL));
        menu.add(new JMenuItem(new AbstractAction(app.getMessage(STR_NEW_CONFIG)) {

            @Override
            public void actionPerformed(ActionEvent e) {
                onNewConfig(configs, menu);
            }
        }));
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

    private void onNewConfig(CTConfigs configs, JPopupMenu menu) {
        CTConfig config = newConfig(controlWindow, app.getMessage(STR_NEW_CONFIG));
        if (config.isValid() && !configs.hasConfig(config)) {
            configs.addConfig(config);
            addConfig(menu, config);
            saveConfigs(configs);
        }
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
    public CTConfigs loadConfigs(boolean reload) {
        return null;
    }

    @Override
    public void help() {
        app.help();
    }

    @Override
    public CTConfig newConfig(Component owner, String title) {
        return app.newConfig(owner, app.getMessage(STR_NEW_CONFIG));
    }

    @Override
    public void saveConfigs(CTConfigs configs) {
        app.saveConfigs(configs);
    }

    @Override
    public void unarm() {
        group.clearSelection();
        app.unarm();
    }

    private static final class CTControlWindow extends JWindow {

        private static final String TOOL_TIP_MANAGER_ENABLE_TOOL_TIP_MODE = "ToolTipManager.enableToolTipMode";

        private static final long serialVersionUID = 1L;

        private final JLabel label;

        volatile boolean reshow;

        private volatile boolean live = true;

        public CTControlWindow(URL imageURL, JPopupMenu menu) {
            super(new JShowingFrame());
            if (imageURL == null) {
                throw new IllegalArgumentException("image not found");
            }
            UIManager.getDefaults().put(TOOL_TIP_MANAGER_ENABLE_TOOL_TIP_MODE, "");
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
            DragHelper.makeDraggable(label, SNAP);
            DragHelper.makeDraggable(this, SNAP);
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

            public JShowingFrame() throws HeadlessException {
            }

            @Override
            public boolean isShowing() {
                return true;
            }
        }
    }

    @SuppressWarnings({"CloneableClassWithoutClone", "SerializableInnerClassWithNonSerializableOuterClass"})
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

    private final class CTConfigMenuItem extends JCheckBoxMenuItem {
        public CTConfigMenuItem(ConfigAction configAction) {
            super(configAction);
        }
    }

}
