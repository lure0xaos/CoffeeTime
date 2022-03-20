package gargoyle.ct

import gargoyle.ct.cmd.args.CTArgs
import gargoyle.ct.cmd.impl.CTCmdImpl
import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.CTConfigs
import gargoyle.ct.config.CTStandardConfigs
import gargoyle.ct.config.convert.CTUnitConverter
import gargoyle.ct.config.convert.impl.CTConfigsConverter
import gargoyle.ct.ex.CTException
import gargoyle.ct.log.Log
import gargoyle.ct.mutex.CTMutex
import gargoyle.ct.mutex.impl.FileMutex
import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.pref.CTPreferences.IconStyle
import gargoyle.ct.pref.impl.CTPreferencesImpl
import gargoyle.ct.pref.impl.prop.CTPrefProperty
import gargoyle.ct.prop.impl.CTPropertyChangeManager
import gargoyle.ct.resource.Resource
import gargoyle.ct.resource.impl.CTConfigResource
import gargoyle.ct.resource.internal.ClasspathResource
import gargoyle.ct.resource.internal.LocalResource
import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.helper.CTTimeHelper
import gargoyle.ct.task.helper.impl.CTTimeHelperImpl
import gargoyle.ct.task.impl.CTTimer
import gargoyle.ct.ui.CTApp
import gargoyle.ct.ui.impl.CTAboutDialog
import gargoyle.ct.ui.impl.CTBlocker
import gargoyle.ct.ui.impl.CTControl
import gargoyle.ct.ui.impl.CTNewConfigDialog
import gargoyle.ct.ui.impl.CTPreferencesDialog
import gargoyle.ct.ui.impl.CTSoundUpdatable
import gargoyle.ct.ui.impl.control.CTShowingFrame
import gargoyle.ct.util.CTNumberUtil
import gargoyle.ct.util.CTStreamUtil
import gargoyle.ct.ver.CTVersionInfo
import gargoyle.ct.ver.impl.CTVersionInfoImpl
import java.awt.Desktop
import java.awt.Frame
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.MessageFormat
import java.util.Locale
import java.util.Objects
import java.util.concurrent.TimeUnit
import javax.swing.JOptionPane
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException

class CT private constructor() : CTApp {
    private val blockers: List<CTBlocker>
    private val configsConverter: CTUnitConverter<CTConfigs> = CTConfigsConverter()
    private val control: CTControl
    private val owner: Frame
    override val preferences: CTPreferences
    private val timeHelper: CTTimeHelper
    private val timer: CTTimer
    override val versionInfo: CTVersionInfo
    private var configResource: Resource? = null
    private val mutex: CTMutex
    private var preferencesDialog: CTPreferencesDialog? = null

    init {
        mutex = FileMutex(CT::class.qualifiedName!!.substringAfterLast('.'))
        if (checkRunning()) {
            throw CTException(MSG_ALREADY_RUNNING)
        }
        val preferences: CTPreferences = CTPreferencesImpl(CT::class)
        this.preferences = preferences
        val timeHelper: CTTimeHelper = CTTimeHelperImpl()
        this.timeHelper = timeHelper
        val blockers: List<CTBlocker> = CTBlocker.forAllDevices(this.preferences)
        this.blockers = blockers
        val updatables: MutableList<CTTaskUpdatable> = ArrayList(blockers)
        owner = CTShowingFrame()
        val control = CTControl(this, owner)
        this.control = control
        preferences.addPropertyChangeListener(control)
        updatables.add(control)
        updatables.add(CTSoundUpdatable(preferences))
        timer = CTTimer(timeHelper, updatables)
        versionInfo = CTVersionInfoImpl()
    }

    private fun checkRunning(): Boolean {
        return !mutex.acquire()
    }

    private fun overridePreferences(cmd: CTArgs): CT {
        for (name in preferences.propertyNames) {
            if (cmd.has(name)) {
                val property: CTPrefProperty<Any> = preferences.getProperty(name)
                property.set(cmd[name, CTNumberUtil.getDefault(property.type())])
            }
        }
        return this
    }

    private fun init(debug: Boolean, fakeTime: Long): CT {
        if (debug) {
            Log.info(MSG_DEBUG_MODE)
        }
        if (fakeTime != 0L) {
            setFakeTime(fakeTime)
            Log.info(MSG_FAKE_TIME_SET_0, fakeTime)
        }
        for (blocker in blockers) {
            blocker.debug(debug)
        }
        return this
    }

    private fun setFakeTime(fakeTime: Long) {
        timeHelper.fakeTime = fakeTime
    }

    private fun start() {
        val property = preferences.config()
        val configs = loadConfigs(false).getConfigs()
        if (property.isPresent && configs.contains(property.get())) {
            control.arm(property.get())
        } else {
            control.arm(configs.iterator().next())
        }
    }

    override fun about() {
        CTAboutDialog(this, owner).showMe()
    }

    override fun arm(config: CTConfig) {
        preferences.config().set(config)
        timer.arm(config, timeHelper.currentTimeMillis())
    }

    override fun browseConfigs() {
        if (configResource != null && Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            val file: File = try {
                val path = Objects.requireNonNull(configResource!!.toURL()).path
                File(if (path.startsWith(SLASH)) path.substring(1) else path)
            } catch (e: IOException) {
                throw CTException(MSG_CANNOT_OPEN_CONFIG, e)
            }
            if (desktop.isSupported(Desktop.Action.EDIT)) {
                try {
                    desktop.edit(file)
                    return
                } catch (e: IOException) {
                    Log.error(e, e.localizedMessage)
                }
            }
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    desktop.open(file)
                } catch (e: IOException) {
                    showError(e)
                }
            }
        }
    }

    private fun showError(e: Exception) {
        Log.error(e, e.message ?: "")
        JOptionPane.showMessageDialog(owner, e.localizedMessage, versionInfo.projectName, JOptionPane.ERROR_MESSAGE)
    }

    override fun exit() {
        for (blocker in blockers) {
            blocker.dispose()
        }
        timer.terminate()
        mutex.release()
        preferences.removePropertyChangeListener(control)
        CTPropertyChangeManager.instance.removePropertyChangeListeners()
    }

    override fun help() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            val loader = CT::class.java.classLoader
            val locale = Locale.getDefault()
            for (resource in arrayOf<Resource?>(
                ClasspathResource(loader, HELP_PAGE).forLocale(locale),
                ClasspathResource(
                    loader,
                    SLASH + HELP_PAGE
                ).forLocale(locale)
            )) {
                if (resource!!.exists()) {
                    try {
                        resource.inputStream.use { stream ->
                            val tempFile = File.createTempFile(CT::class.qualifiedName!!, DOT + HTML)
                            Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                            Desktop.getDesktop().browse(tempFile.toURI())
                            tempFile.deleteOnExit()
                        }
                    } catch (ex: IOException) {
                        Log.error(ex, PAGE_0_NOT_FOUND, HELP_PAGE)
                    }
                    return
                } else {
                    Log.debug(PAGE_0_NOT_FOUND, HELP_PAGE + ": " + resource.location)
                }
            }
            Log.error(PAGE_0_NOT_FOUND, HELP_PAGE)
        }
    }

    override fun loadConfigs(reload: Boolean): CTConfigs {
        var configs: CTConfigs
        if (configResource == null || reload) {
            var configResource: CTConfigResource? = CTConfigResource.findLocalConfig(CONFIG_NAME, false)
            if (configResource != null && configResource.exists()) {
                try {
                    configResource.inputStream.use { stream ->
                        configs = configsConverter.parse(CTStreamUtil.convertStreamToString(stream, CONFIG_CHARSET))
                        if (configs.getConfigs().isEmpty()) {
                            configs = CTStandardConfigs
                        }
                    }
                } catch (ex: IOException) {
                    Log.error(ex, MSG_CANNOT_LOAD_0, configResource)
                    configs = CTStandardConfigs
                }
            } else {
                Log.warn(NOT_FOUND_0, Objects.requireNonNullElse(configResource, CONFIG_NAME)!!)
                configs = CTStandardConfigs
                configResource =
                    CTConfigResource.forURL(LocalResource.homeDirectoryLocation, CONFIG_NAME)
                saveConfigs(configs, configResource)
            }
            this.configResource = configResource
        } else {
            try {
                configResource!!.inputStream.use { stream ->
                    configs = configsConverter.parse(CTStreamUtil.convertStreamToString(stream, CONFIG_CHARSET))
                }
            } catch (ex: IOException) {
                throw CTException(MSG_CANNOT_READ_CONFIG, ex)
            }
        }
        return configs
    }

    override fun saveConfigs(configs: CTConfigs) {
        saveConfigs(configs, configResource)
    }

    override fun showNewConfig(): CTConfig? {
        try {
            return CTNewConfigDialog(owner, preferences, this).showMe()
        } catch (ex: IllegalArgumentException) {
            Log.error(ex.message ?: "")
        }
        return null
    }

    override fun showPreferences() {
        if (preferencesDialog == null) {
            preferencesDialog = CTPreferencesDialog(this, owner)
        }
        preferencesDialog!!.showMe()
    }

    override fun unarm() {
        timer.unarm()
    }

    private fun saveConfigs(configs: CTConfigs?, configResource: Resource?) {
        if (configResource != null) {
            try {
                configResource.outputStream.use { stream ->
                    CTStreamUtil.write(
                        stream,
                        configsConverter.format(TimeUnit.MINUTES, configs!!),
                        CONFIG_CHARSET
                    )
                }
            } catch (ex: IOException) {
                Log.warn(NOT_FOUND_0, configResource)
            }
        }
    }

    override val bigIcon: URL?
        get() {
            val bw = preferences.iconStyle()[IconStyle.BW].path
            return CT::class.java.getResource(
                MessageFormat.format(URL_ICON_BIG_W, bw)
            )
        }
    override val mediumIcon: URL?
        get() {
            val bw = preferences.iconStyle()[IconStyle.BW].path
            return CT::class.java.getResource(
                MessageFormat.format(URL_ICON_MEDIUM_W, bw)
            )
        }
    override val smallIcon: URL?
        get() {
            val bw = preferences.iconStyle()[IconStyle.BW].path
            return CT::class.java.getResource(
                MessageFormat.format(URL_ICON_SMALL_W, bw)
            )
        }

    companion object {
        private val CONFIG_CHARSET = StandardCharsets.UTF_8.name()
        private const val CONFIG_NAME = "CT.cfg"
        private const val DOT = "."
        private const val HELP_PAGE = "html/help.html"
        private const val HTML = "html"
        private const val MSG_ALREADY_RUNNING = "App already running"
        private const val MSG_CANNOT_LOAD_0 = "Cannot load {0}"
        private const val MSG_DEBUG_MODE = "debug mode"
        private const val MSG_FAKE_TIME_SET_0 = "fake time set {0, time, HH:mm:ss}"
        private const val NOT_FOUND_0 = "Not found {0}"
        private const val MSG_CANNOT_SET_LOOK_AND_FEEL = "Cannot set LookAndFeel"
        private const val MSG_CANNOT_OPEN_CONFIG = "Cannot open config"
        private const val MSG_CANNOT_READ_CONFIG = "Cannot read config"
        private const val PAGE_0_NOT_FOUND = "Page {0} not found"
        private const val SLASH = "/"
        private const val URL_ICON_BIG_W = "/icon/{0}/64/icon64.png"
        private const val URL_ICON_MEDIUM_W = "/icon/{0}/32/icon32.png"
        private const val URL_ICON_SMALL_W = "/icon/{0}/16/icon16.png"

        @JvmStatic
        fun main(args: Array<String>) {
            setSystemLookAndFeel()
            val cmd = CTCmdImpl(args)
            CT().init(cmd.isDebug, cmd.fakeTime).overridePreferences(cmd).start()
        }

        private fun setSystemLookAndFeel() {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            } catch (ex: ClassNotFoundException) {
                throw CTException(MSG_CANNOT_SET_LOOK_AND_FEEL, ex)
            } catch (ex: UnsupportedLookAndFeelException) {
                throw CTException(MSG_CANNOT_SET_LOOK_AND_FEEL, ex)
            } catch (ex: IllegalAccessException) {
                throw CTException(MSG_CANNOT_SET_LOOK_AND_FEEL, ex)
            } catch (ex: InstantiationException) {
                throw CTException(MSG_CANNOT_SET_LOOK_AND_FEEL, ex)
            }
        }
    }
}
