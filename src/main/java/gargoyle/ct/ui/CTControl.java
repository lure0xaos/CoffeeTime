package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.helper.CTDragHelper;
import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.util.CTTimeUtil;
import gargoyle.ct.util.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class CTControl implements CTControlActions, CTTaskUpdatable, PreferenceChangeListener {
    private static final String LOC_MESSAGES = "control";
    private static final int SNAP = 20;
    private static final String STR_EXIT = "exit";
    private static final String STR_EXIT_TOOLTIP = "exit.tooltip";
    private static final String STR_HELP = "help";
    private static final String STR_HELP_TOOLTIP = "help.tooltip";
    private static final String STR_NEW_CONFIG = "new-config";
    private static final String STR_NEW_CONFIG_TOOLTIP = "new-config.tooltip";
    private static final String STR_PREFERENCES = "preferences";
    private static final String STR_PREFERENCES_TOOLTIP = "preferences.tooltip";
    private static final String STR_TITLE = "title";
    private static final String STR_UNARM = "unarm";
    private static final String STR_UNARM_TOOLTIP = "unarm.tooltip";
    private static final String URL_ICON = "/icon64.png";
    private final CTControlActions app;
    private final CTControlWindow controlWindow;
    private final ButtonGroup group;
    private final MessageProvider messages;

    public CTControl(CTControlActions app) {
        this.app = app;
        messages = new CTMessages(LOC_MESSAGES);
        group = new ButtonGroup();
        controlWindow = new CTControlWindow(app, CTControl.class.getResource(URL_ICON), createMenu(app.loadConfigs(false)));
        controlWindow.setVisible(true);
    }

    private JPopupMenu createMenu(CTConfigs configs) {
        JPopupMenu menu = new JPopupMenu();
        addConfigs(menu, configs);
        menu.add(new JSeparator(SwingConstants.HORIZONTAL));
        menu.add(new CTMenuItem(new CTAction(messages.getMessage(STR_NEW_CONFIG), messages.getMessage(STR_NEW_CONFIG_TOOLTIP)) {
            private static final long serialVersionUID = 1121004649381891357L;

            @Override
            public void actionPerformed(ActionEvent e) {
                onNewConfig(configs, menu);
            }
        }));
        menu.add(new JSeparator(SwingConstants.HORIZONTAL));
        menu.add(new CTMenuItem(new CTAction(messages.getMessage(STR_UNARM), messages.getMessage(STR_UNARM_TOOLTIP)) {
            private static final long serialVersionUID = -4330571111080076360L;

            @Override
            public void actionPerformed(ActionEvent e) {
                unarm();
            }
        }));
        menu.add(new CTMenuItem(new CTAction(messages.getMessage(STR_PREFERENCES), messages.getMessage(STR_PREFERENCES_TOOLTIP)) {
            private static final long serialVersionUID = 8645604731109700568L;

            @Override
            public void actionPerformed(ActionEvent e) {
                showPreferences(controlWindow.getOwner(), messages.getMessage(STR_PREFERENCES));
            }
        }));
        menu.add(new CTMenuItem(new CTAction(messages.getMessage(STR_HELP), messages.getMessage(STR_HELP_TOOLTIP)) {
            private static final long serialVersionUID = 5717750136378884217L;

            @Override
            public void actionPerformed(ActionEvent e) {
                help();
            }
        }));
        menu.add(new CTMenuItem(new CTAction(messages.getMessage(STR_EXIT), messages.getMessage(STR_EXIT_TOOLTIP)) {
            private static final long serialVersionUID = 6450213490024118820L;

            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        }));
        return menu;
    }

    private void addConfigs(JPopupMenu menu, CTConfigs configs) {
        for (CTConfig config : configs.getConfigs()) {
            addConfig(menu, config);
        }
    }

    private void addConfig(JPopupMenu menu, CTConfig config) {
        CTConfigMenuItem menuItem = new CTConfigMenuItem(new ConfigAction(config));
        group.add(menuItem);
        menu.insert(menuItem, group.getButtonCount() - 1);
    }

    private void onNewConfig(CTConfigs configs, JPopupMenu menu) {
        CTConfig config = newConfig(controlWindow.getOwner(), messages.getMessage(STR_TITLE));
        if (config != null && config.isValid() && !configs.hasConfig(config)) {
            configs.addConfig(config);
            addConfig(menu, config);
            saveConfigs(configs);
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

    @Override
    public void exit() {
        controlWindow.setVisible(false);
        controlWindow.dispose();
        app.unarm();
        app.exit();
    }

    @Override
    public void help() {
        app.help();
    }

    @Override
    public CTConfigs loadConfigs(boolean reload) {
        return null;
    }

    @Override
    public CTConfig newConfig(Window owner, String title) {
        return app.newConfig(owner, title);
    }

    @Override
    public CTPreferences preferences() {
        return app.preferences();
    }

    @Override
    public void saveConfigs(CTConfigs configs) {
        app.saveConfigs(configs);
    }

    @Override
    public void showPreferences(Window owner, String title) {
        app.showPreferences(owner, title);
    }

    @Override
    public void unarm() {
        group.clearSelection();
        app.unarm();
    }

    private AbstractButton findItem(CTConfig config) {
        for (AbstractButton button : Collections.list(group.getElements())) {
            Action action = button.getAction();
            if (action instanceof ConfigAction) {
                if (Objects.equals(((ConfigAction) action).getConfig(), config)) {
                    return button;
                }
            }
        }
        return null;
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
    public void preferenceChange(PreferenceChangeEvent evt) {
        controlWindow.transparency(true);
    }

    private abstract static class CTAction extends AbstractAction {
        private static final long serialVersionUID = -5252756701531090882L;

        protected CTAction() {
        }

        public CTAction(String text) {
            setText(text);
            setTooltipText(text);
        }

        public CTAction(String text, Icon icon) {
            setText(text);
            setTooltipText(text);
            setIcon(icon);
        }

        public CTAction(String text, String tooltipText, Icon icon) {
            setText(text);
            setTooltipText(tooltipText);
            setIcon(icon);
        }

        public CTAction(String text, String tooltipText) {
            setText(text);
            setTooltipText(tooltipText);
        }

        public CTAction clone() throws CloneNotSupportedException {
            return (CTAction) super.clone();
        }

        public void init(AbstractButton menuItem) {
            menuItem.setAction(this);
            menuItem.setText(getText());
            menuItem.setToolTipText(getTooltipText());
            Icon icon = getIcon();
            if (icon != null) {
                menuItem.setIcon(icon);
            }
        }

        protected String getText() {
            return String.valueOf(getValue(Action.NAME));
        }

        protected void setText(String text) {
            putValue(Action.NAME, text);
        }

        protected String getTooltipText() {
            return String.valueOf(getValue(Action.SHORT_DESCRIPTION));
        }

        protected void setTooltipText(String text) {
            putValue(Action.SHORT_DESCRIPTION, text);
        }

        protected Icon getIcon() {
            return (Icon) getValue(Action.SMALL_ICON);
        }

        protected void setIcon(Icon icon) {
            putValue(Action.SMALL_ICON, icon);
        }
    }

    private static final class CTConfigMenuItem extends JCheckBoxMenuItem {
        private static final long serialVersionUID = -2199156620390967976L;

        public CTConfigMenuItem(ConfigAction action) {
            super(action);
        }
    }

    private static final class CTControlWindow extends JWindow {
        private static final String MSG_TRANSPARENCY_NOT_SUPPORTED = "transparency not supported";
        private static final String TOOL_TIP_MANAGER_ENABLE_TOOL_TIP_MODE = "ToolTipManager.enableToolTipMode";
        private static final long serialVersionUID = 1L;
        private final transient CTControlActions app;
        private final JLabel label;
        volatile boolean reshow;
        private volatile boolean live = true;

        public CTControlWindow(CTControlActions app, URL imageURL, JPopupMenu menu) {
            super(new JShowingFrame());
            this.app = app;
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
            getOwner().setLocation(getLocation());
            CTDragHelper.makeDraggable(label, SNAP);
            CTDragHelper.makeDraggable(this, SNAP);
            ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
            toolTipManager.setDismissDelay(1000);
            toolTipManager.setInitialDelay(100);
            toolTipManager.setReshowDelay(100);
            toolTipManager.setEnabled(true);
            toolTipManager.setLightWeightPopupEnabled(true);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    reshow = true;
                    transparency(false);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    reshow = false;
                    transparency(true);
                }
            });
        }

        private void transparency(boolean transparent) {
            CTPreferences preferences = app.preferences();
            try {
                setOpacity(preferences.isTransparency() && transparent ? preferences.getTransparencyLevel() : 1);
            } catch (UnsupportedOperationException e) {
                Log.warn(e, MSG_TRANSPARENCY_NOT_SUPPORTED);
            }
        }

        public void setComponentPopupMenu(JPopupMenu menu) {
            label.setComponentPopupMenu(menu);
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

    private static final class CTMenuItem extends JMenuItem {
        private static final long serialVersionUID = -5435250463745762683L;

        public CTMenuItem(CTAction action) {
            action.init(this);
        }
    }

    private final class ConfigAction extends CTAction {
        private static final long serialVersionUID = 8001396484814809015L;
        private final CTConfig config;

        public ConfigAction(CTConfig config) {
            this.config = config;
            String text = config.getName();
            setText(text);
            setTooltipText(text);
        }

        public ConfigAction clone() throws CloneNotSupportedException {
            return (ConfigAction) super.clone();
        }

        public CTConfig getConfig() {
            return config;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            arm(config);
        }




    }
}
