package gargoyle.ct.messages.impl

import gargoyle.ct.log.Log
import gargoyle.ct.messages.LocaleProvider
import gargoyle.ct.messages.MessageProvider
import gargoyle.ct.messages.MessageProviderEx
import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.prop.CTObservableProperty
import java.text.MessageFormat
import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle

class CTMessages(private val localeProvider: LocaleProvider, private val parent: MessageProvider?, baseName: String) :
    MessageProviderEx {
    private lateinit var messages: ResourceBundle
    private lateinit var baseName: String

    constructor(baseName: String) : this(CTFixedLocaleProvider(), null, baseName)

    init {
        load(baseName)
        localeProvider.locale()
            .addPropertyChangeListener { reload() }
    }

    private fun load(baseName: String) {
        this.baseName = baseName
        try {
            messages = ResourceBundle.getBundle(baseName, localeProvider.getLocale())
        } catch (ex: MissingResourceException) {
            if (parent == null) {
                throw MissingResourceException(
                    MessageFormat.format(MSG_NO_BUNDLE, baseName),
                    baseName,
                    ex.key
                )
            } else {
                Log.warn(MessageFormat.format(MSG_NO_BUNDLE, baseName))
            }
        }
    }

    private fun reload() {
        load(baseName)
    }

    constructor(parent: MessageProvider?, baseName: String) : this(CTFixedLocaleProvider(), parent, baseName)
    constructor(localeProvider: LocaleProvider, baseName: String) : this(localeProvider, null, baseName)

    override fun getLocale(): Locale {
        return localeProvider.getLocale()
    }

    override fun setLocale(locale: Locale) {
        localeProvider.setLocale(locale)
    }

    override fun locale(): CTObservableProperty<SupportedLocales> {
        return localeProvider.locale()
    }

    override fun getMessage(message: String, vararg args: Any): String {
        if (messages.locale != localeProvider.getLocale()) {
            reload()
        }
        return try {
            val pattern = messages.getString(message)
            try {
                MessageFormat.format(pattern, *args)
            } catch (ex: IllegalArgumentException) {
                throw IllegalArgumentException(
                    MessageFormat.format(
                        CAN_T_PARSE_MESSAGE_0_1_2,
                        message,
                        pattern,
                        args.contentToString()
                    ), ex
                )
            }
        } catch (ex: MissingResourceException) {
            if (parent == null) {
                val bundle = messages.baseBundleName
                throw MissingResourceException(
                    MessageFormat.format(MSG_NO_MESSAGE, bundle, message),
                    bundle,
                    message
                )
            } else {
                parent.getMessage(message, *args)
            }
        }
    }

    companion object {
        private const val CAN_T_PARSE_MESSAGE_0_1_2 = "can''t parse message:{0}->{1}({2})"
        private const val MSG_NO_BUNDLE = "Can''t find bundle {0}"
        private const val MSG_NO_MESSAGE = "Can''t find resource for bundle {0}, key {1}"
    }
}
