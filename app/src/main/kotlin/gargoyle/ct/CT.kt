package gargoyle.ct

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.CTConfigs
import gargoyle.ct.config.CTStandardConfigs
import gargoyle.ct.ex.CTException
import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.preferences.CTPreferences.IconStyle
import gargoyle.ct.preferences.impl.CTPreferencesImpl
import gargoyle.ct.resource.impl.CTConfigResource
import gargoyle.ct.task.CTTaskUpdatable
import gargoyle.ct.task.helper.CTTimeHelper
import gargoyle.ct.task.helper.impl.CTTimeHelperImpl
import gargoyle.ct.task.impl.CTTimer
import gargoyle.ct.ui.CTApp
import gargoyle.ct.ui.impl.*
import gargoyle.ct.ui.impl.control.CTShowingFrame
import gargoyle.ct.util.args.CTArgs
import gargoyle.ct.util.cmd.impl.CTCmdImpl
import gargoyle.ct.util.log.Log
import gargoyle.ct.util.mutex.CTMutex
import gargoyle.ct.util.mutex.impl.FileMutex
import gargoyle.ct.util.resource.Resource
import gargoyle.ct.util.resource.internal.ClassResource
import gargoyle.ct.util.resource.internal.LocalResource
import gargoyle.ct.util.util.className
import gargoyle.ct.util.util.getResource
import gargoyle.ct.util.util.getResourceBundle
import gargoyle.ct.util.util.simpleClassName
import gargoyle.ct.util.ver.CTVersionInfo
import gargoyle.ct.util.ver.impl.CTVersionInfoImpl
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.awt.Desktop
import java.awt.Frame
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.MessageFormat
import java.util.*
import javax.swing.JOptionPane
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.full.memberProperties

class CT private constructor() : CTApp {
    private val blockers: List<CTBlocker>
    private val control: CTControl
    private val owner: Frame = CTShowingFrame()
    override val preferences: CTPreferences
    private val timeHelper: CTTimeHelper
    private val timer: CTTimer
    override val versionInfo: CTVersionInfo
    private var configResource: Resource? = null
    private val mutex: CTMutex
    private val preferencesDialog: CTPreferencesDialog by lazy { CTPreferencesDialog(this, owner) }

    init {
        mutex = FileMutex(CT::class.simpleClassName)
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
        val control = CTControl(this, owner)
        this.control = control
        updatables.add(control)
        updatables.add(CTSoundUpdatable(preferences))
        timer = CTTimer(timeHelper, updatables)
        versionInfo = CTVersionInfoImpl { CT::class.getResourceBundle(LOC_VERSION, it) }
    }

    private fun checkRunning(): Boolean {
        return !mutex.acquire()
    }

    @Suppress("UNCHECKED_CAST")
    private fun overridePreferences(cmd: CTArgs): CT {
        preferences::class.memberProperties
            .filter { cmd.has(it.name) && it is KMutableProperty0<*> }
            .map { (it as KMutableProperty0<Any>) }
            .forEach { it.set(cmd[it.name, it.get()]) }
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
        val property = preferences.config
        val configs = loadConfigs(false).getConfigs()
        if (configs.contains(property)) {
            control.arm(property)
        } else {
            configs.firstOrNull()?.let { control.arm(it) }
        }
    }

    override fun about() {
        CTAboutDialog(this, owner).showMe()
    }

    override fun arm(config: CTConfig) {
        preferences.config = config
        timer.arm(config, timeHelper.currentTimeMillis)
    }

    override fun browseConfigs() {
        if (configResource != null && Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            val file: File = try {
                val path = configResource!!.toURL().path
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
    }

    override fun help() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            val loader = CT::class
            val locale = Locale.getDefault()
            for (resource in arrayOf(
                ClassResource(loader, HELP_PAGE).forLocale(locale)
            )) {
                if (resource.exists()) {
                    try {
                        resource.inputStream.use { stream ->
                            val tempFile = File.createTempFile(CT::class.className, DOT + HTML)
                            Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                            Desktop.getDesktop().browse(tempFile.toURI())
                            tempFile.deleteOnExit()
                        }
                    } catch (ex: Exception) {
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

    @OptIn(ExperimentalSerializationApi::class)
    override fun loadConfigs(reload: Boolean): CTConfigs {
        var configs: CTConfigs
        if (configResource == null || reload) {
            var configResource: CTConfigResource? = CTConfigResource.findLocalConfig(CONFIG_NAME, false)
            if (configResource != null && configResource.exists()) {
                try {
                    configResource.inputStream.use { stream ->
                        configs = Json.decodeFromStream(stream)
                        if (configs.getConfigs().isEmpty()) {
                            configs = CTStandardConfigs
                        }
                    }
                } catch (ex: IOException) {
                    Log.error(ex, MSG_CANNOT_LOAD_0, configResource)
                    configs = CTStandardConfigs
                }
            } else {
                Log.warn(NOT_FOUND_0, configResource ?: CONFIG_NAME)
                configs = CTStandardConfigs
                configResource =
                    CTConfigResource.forURL(LocalResource.homeDirectoryLocation, CONFIG_NAME)
                saveConfigs(configs, configResource)
            }
            this.configResource = configResource
        } else {
            try {
                configResource!!.inputStream.use { stream ->
                    configs = Json.decodeFromStream(stream)
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
        preferencesDialog.showMe()
    }

    override fun unarm() {
        timer.unarm()
    }

    @Suppress("OPT_IN_USAGE")
    private fun saveConfigs(configs: CTConfigs, configResource: Resource?) {
        if (configResource != null) {
            try {
                configResource.outputStream.use {
                    Json.encodeToStream(configs, it)
                }
            } catch (ex: IOException) {
                Log.warn(NOT_FOUND_0, configResource)
            }
        }
    }

    override val bigIcon: URL
        get() =
            CT::class.getResource(MessageFormat.format(URL_ICON_BIG_W, (preferences.iconStyle ?: IconStyle.BW).path))
    override val mediumIcon: URL
        get() =
            CT::class.getResource(MessageFormat.format(URL_ICON_MEDIUM_W, (preferences.iconStyle ?: IconStyle.BW).path))
    override val smallIcon: URL
        get() =
            CT::class.getResource(MessageFormat.format(URL_ICON_SMALL_W, (preferences.iconStyle ?: IconStyle.BW).path))

    companion object {
        private val CONFIG_CHARSET = StandardCharsets.UTF_8.name()
        private const val CONFIG_NAME = "CT.cfg"
        private const val DOT = "."
        private const val HELP_PAGE = "help.html"
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
        private const val URL_ICON_BIG_W = "{0}-icon64.png"
        private const val URL_ICON_MEDIUM_W = "{0}-icon32.png"
        private const val URL_ICON_SMALL_W = "{0}-icon16.png"
        private const val LOC_VERSION = "version"

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
