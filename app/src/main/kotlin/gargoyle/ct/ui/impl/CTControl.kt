package gargoyle.ct.ui.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.CTConfigs
import gargoyle.ct.messages.locale.CTPreferencesLocaleProvider
import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.impl.CTTask
import gargoyle.ct.ui.CTApp
import gargoyle.ct.ui.CTBlockerTextProvider
import gargoyle.ct.ui.CTControlActions
import gargoyle.ct.ui.CTControlWindow
import gargoyle.ct.ui.impl.control.*
import gargoyle.ct.util.messages.impl.CTMessages
import gargoyle.ct.util.util.getResourceBundle
import java.awt.Frame
import java.awt.event.ActionEvent
import java.util.*
import javax.swing.*

class CTControl(app: CTApp, owner: Frame) : CTControlActions, CTTaskUpdatable {
    private val app: CTControlActions
    private val controlWindow: CTControlWindow
    private val group: ButtonGroup
    private val textProvider: CTBlockerTextProvider
    private lateinit var menu: JPopupMenu
    private lateinit var stopMenuItem: CTLocalizableMenuItem

    init {
        this.app = app
        val preferences = app.preferences
        textProvider = CTBlockerTextProviderImpl(preferences)
        group = ButtonGroup()
        controlWindow = CTControlWindowImpl(
            owner, preferences, app,
            createMenu(
                CTMessages(
                    { CTApp::class.getResourceBundle(LOC_MESSAGES, it) },
                    CTPreferencesLocaleProvider(preferences)
                ), app.loadConfigs(false)
            )
        )
        controlWindow.showMe()
    }

    private fun createMenu(messages: CTMessages, configs: CTConfigs): JPopupMenu {
        menu = JPopupMenu()
        addConfigs(menu, configs, null)
        menu.add(JSeparator(SwingConstants.HORIZONTAL))
        menu.add(
            CTLocalizableMenuItem(messages,
                object : CTLocalizableAction(messages, STR_NEW_CONFIG, STR_NEW_CONFIG_TOOLTIP) {
                    override fun actionPerformed(e: ActionEvent) {
                        onNewConfig(configs, menu)
                    }
                })
        )
        menu.add(JSeparator(SwingConstants.HORIZONTAL))
        stopMenuItem = CTLocalizableMenuItem(messages, object : CTLocalizableAction(
            messages, STR_UNARM,
            STR_UNARM_TOOLTIP
        ) {
            override fun actionPerformed(e: ActionEvent) {
                unarm()
            }
        })
        stopMenuItem.isEnabled = false
        menu.add(stopMenuItem)
        menu.add(JSeparator(SwingConstants.HORIZONTAL))
        menu.add(
            CTLocalizableMenuItem(messages,
                object : CTLocalizableAction(messages, STR_BROWSE_CONFIGS, STR_BROWSE_CONFIGS_TOOLTIP) {
                    override fun actionPerformed(e: ActionEvent) {
                        browseConfigs()
                    }
                })
        )
        menu.add(JSeparator(SwingConstants.HORIZONTAL))
        menu.add(
            CTLocalizableMenuItem(messages,
                object : CTLocalizableAction(messages, STR_PREFERENCES, STR_PREFERENCES_TOOLTIP) {
                    override fun actionPerformed(e: ActionEvent) {
                        showPreferences()
                    }
                })
        )
        menu.add(CTLocalizableMenuItem(messages, object : CTLocalizableAction(messages, STR_HELP, STR_HELP_TOOLTIP) {
            override fun actionPerformed(e: ActionEvent) {
                help()
            }
        }))
        menu.add(CTLocalizableMenuItem(messages, object : CTLocalizableAction(messages, STR_ABOUT, "") {
            override fun actionPerformed(e: ActionEvent) {
                about()
            }
        }))
        menu.add(CTLocalizableMenuItem(messages, object : CTLocalizableAction(messages, STR_EXIT, STR_EXIT_TOOLTIP) {
            override fun actionPerformed(e: ActionEvent) {
                exit()
            }
        }))
        return menu
    }

    private fun addConfigs(menu: JPopupMenu, configs: CTConfigs, toArm: CTConfig?) {
        require(toArm == null || configs.hasConfig(toArm)) { "invalid config" }
        configs.getConfigs().forEach { addConfig(menu, it) }
        toArm?.let { arm(it) }
    }

    private fun addConfig(menu: JPopupMenu, config: CTConfig) {
        val menuItem = CTConfigMenuItem(CTConfigAction(this, config))
        group.add(menuItem)
        menu.insert(menuItem, group.buttonCount - 1)
    }

    private fun onNewConfig(configs: CTConfigs, menu: JPopupMenu) {
        val config = showNewConfig()
        if (config != null && config.isValid && !configs.hasConfig(config)) {
            configs.addConfig(config)
            addConfig(menu, config)
            saveConfigs(configs)
        }
    }

    override fun about() {
        app.about()
    }

    override fun arm(config: CTConfig) {
        val item = findItem(config)
        if (item != null) {
            stopMenuItem.isEnabled = true
            group.clearSelection()
            group.setSelected(item.model, true)
            if (item.action is CTConfigAction) {
                app.arm((item.action as CTConfigAction).config)
            }
        }
    }

    override fun browseConfigs() {
        app.browseConfigs()
    }

    override fun exit() {
        controlWindow.destroy()
        app.unarm()
        app.exit()
    }

    override fun help() {
        app.help()
    }

    override fun loadConfigs(reload: Boolean): CTConfigs {
        val configs = app.loadConfigs(reload)
        if (reload) {
            replaceConfigs(menu, configs)
        }
        return configs
    }

    override fun saveConfigs(configs: CTConfigs) {
        app.saveConfigs(configs)
    }

    override fun showNewConfig(): CTConfig? = app.showNewConfig()

    override fun showPreferences(): Unit = app.showPreferences()

    override fun unarm() {
        stopMenuItem.isEnabled = false
        group.clearSelection()
        app.unarm()
    }

    private fun replaceConfigs(menu: JPopupMenu, configs: CTConfigs) {
        val selectedConfig = selectedConfig
        removeConfigs(menu)
        addConfigs(menu, configs, selectedConfig)
        // if (selectedConfig != null && findItem(selectedConfig) != null) {
        //     arm(selectedConfig);
        // }
    }

    private val selectedConfig: CTConfig?
        get() {
            var selectedConfig: CTConfig? = null
            val elements: List<AbstractButton> = Collections.list(group.elements)
            for (element in elements) {
                require(element is CTConfigMenuItem) { "not a config added" }
                if (element.model.isSelected && element.action is CTConfigAction) {
                    selectedConfig = (element.action as CTConfigAction).config
                }
            }
            return selectedConfig
        }

    private fun removeConfigs(menu: JPopupMenu) {
        group.elements.toList().forEach { element ->
            require(element is CTConfigMenuItem) { "not a config to remove" }
            group.remove(element)
            menu.remove(element)
        }
    }

    private fun findItem(config: CTConfig): AbstractButton? =
        group.elements.toList().firstOrNull {
            it.action is CTConfigAction && (it.action as CTConfigAction).config == config
        }

    override fun doUpdate(task: CTTask, currentMillis: Long) {
        controlWindow.setToolTipText(textProvider.getToolTipText(task, currentMillis))
        //        lblInfo.setText(textProvider.getInfoText(task, currentMillis));
        val visible = textProvider.isVisible(task, currentMillis)
        controlWindow.setTextMode(visible)
        if (visible) {
            controlWindow.showText(
                textProvider.getColor(task, currentMillis),
                textProvider.getBlockerText(task, currentMillis, false)
            )
        }
    }

    companion object {
        private const val LOC_MESSAGES = "control"
        private const val STR_ABOUT = "about"
        private const val STR_BROWSE_CONFIGS = "browse-configs"
        private const val STR_BROWSE_CONFIGS_TOOLTIP = "browse-configs.tooltip"
        private const val STR_EXIT = "exit"
        private const val STR_EXIT_TOOLTIP = "exit.tooltip"
        private const val STR_HELP = "help"
        private const val STR_HELP_TOOLTIP = "help.tooltip"
        private const val STR_NEW_CONFIG = "new-config"
        private const val STR_NEW_CONFIG_TOOLTIP = "new-config.tooltip"
        private const val STR_PREFERENCES = "preferences"
        private const val STR_PREFERENCES_TOOLTIP = "preferences.tooltip"
        private const val STR_UNARM = "unarm"
        private const val STR_UNARM_TOOLTIP = "unarm.tooltip"
    }
}
