package gargoyle.ct.ui.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.PropertyChangeEvent;
import gargoyle.ct.pref.PropertyChangeListener;
import gargoyle.ct.task.CTTaskUpdatable;
import gargoyle.ct.task.impl.CTTask;
import gargoyle.ct.ui.CTBlockerTextProvider;
import gargoyle.ct.ui.CTControlActions;
import gargoyle.ct.ui.CTControlWindow;
import gargoyle.ct.ui.impl.control.CTAction;
import gargoyle.ct.ui.impl.control.CTConfigAction;
import gargoyle.ct.ui.impl.control.CTConfigMenuItem;
import gargoyle.ct.ui.impl.control.CTControlWindowImpl;
import gargoyle.ct.ui.impl.control.CTMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Objects;

public class CTControl implements CTControlActions, CTTaskUpdatable, PropertyChangeListener {
    private static final String LOC_MESSAGES = "messages.control";
    private static final String STR_EXIT = "exit";
    private static final String STR_EXIT_TOOLTIP = "exit.tooltip";
    private static final String STR_HELP = "help";
    private static final String STR_HELP_TOOLTIP = "help.tooltip";
    private static final String STR_NEW_CONFIG = "new-config";
    private static final String STR_NEW_CONFIG_TOOLTIP = "new-config.tooltip";
    private static final String STR_PREFERENCES = "preferences";
    private static final String STR_PREFERENCES_TOOLTIP = "preferences.tooltip";
    private static final String STR_UNARM = "unarm";
    private static final String STR_UNARM_TOOLTIP = "unarm.tooltip";
    private static final String URL_ICON = "/icon/64/icon64.png";
    private final CTControlActions app;
    private final CTControlWindow controlWindow;
    private final ButtonGroup group;
    private final MessageProvider messages;
    private final CTBlockerTextProvider textProvider;

    public CTControl(CTControlActions app, Frame owner) {
        this.app = app;
        textProvider = new CTBlockerTextProvider();
        messages = new CTMessages(LOC_MESSAGES);
        group = new ButtonGroup();
        controlWindow = new CTControlWindowImpl(owner, app.preferences(), CTControl.class.getResource(URL_ICON), createMenu(app.loadConfigs(false)));
        controlWindow.showMe();
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
                showPreferences();
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
        CTConfigMenuItem menuItem = new CTConfigMenuItem(new CTConfigAction(this, config));
        group.add(menuItem);
        menu.insert(menuItem, group.getButtonCount() - 1);
    }

    private void onNewConfig(CTConfigs configs, JPopupMenu menu) {
        CTConfig config = showNewConfig();
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
        controlWindow.destroy();
        app.unarm();
        app.exit();
    }

    @Override
    public void help() {
        app.help();
    }

    @Override
    public CTConfigs loadConfigs(boolean reload) {
        return app.loadConfigs(reload);
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
    public CTConfig showNewConfig() {
        return app.showNewConfig();
    }

    @Override
    public void showPreferences() {
        app.showPreferences();
    }

    @Override
    public void unarm() {
        group.clearSelection();
        app.unarm();
    }

    private AbstractButton findItem(CTConfig config) {
        for (AbstractButton button : Collections.list(group.getElements())) {
            Action action = button.getAction();
            if (action instanceof CTConfigAction) {
                if (Objects.equals(((CTConfigAction) action).getConfig(), config)) {
                    return button;
                }
            }
        }
        return null;
    }

    @Override
    public void doUpdate(CTTask task, long currentMillis) {
        controlWindow.setToolTipText(textProvider.getToolTipText(task, currentMillis));
//        lblInfo.setText(textProvider.getInfoText(task, currentMillis));
        boolean visible = textProvider.isVisible(task, currentMillis);
        controlWindow.setTextMode(visible);
        if (visible) {
            controlWindow.showText(textProvider.getColor(task, currentMillis), textProvider.getBlockerText(task, currentMillis, false));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        controlWindow.propertyChange(event);
    }
}
