package gargoyle.ct.util.messages.impl

import gargoyle.ct.util.messages.LocaleProvider
import gargoyle.ct.util.messages.MessageProvider
import gargoyle.ct.util.messages.MessageProviderEx
import gargoyle.ct.util.messages.SupportedLocales
import gargoyle.ct.util.prop.CTObservableProperty
import gargoyle.ct.util.util.get
import java.text.MessageFormat
import java.util.*

class CTMessages(
    resourceBundleLoader: (Locale) -> ResourceBundle,
    private val localeProvider: LocaleProvider = CTFixedLocaleProvider(),
    private val parent: MessageProvider? = null
) : MessageProviderEx {

    private var loader: (Locale) -> ResourceBundle = resourceBundleLoader
    private lateinit var messages: ResourceBundle

    private fun reload() {
        messages = loader(localeProvider.getLocale())
    }

    override fun getLocale(): Locale = localeProvider.getLocale()

    override fun setLocale(locale: Locale): Unit = localeProvider.setLocale(locale)

    override fun locale(): CTObservableProperty<SupportedLocales> = localeProvider.locale()

    override fun getMessage(message: String, vararg args: Any): String {
        if (messages.locale != localeProvider.getLocale()) {
            reload()
        }
        return try {
            val pattern = messages[message]!!
            try {
                MessageFormat.format(pattern, *args)
            } catch (ex: IllegalArgumentException) {
                throw IllegalArgumentException("can''t parse message:$message->$pattern(${args.contentToString()})", ex)
            }
        } catch (ex: MissingResourceException) {
            if (parent == null) {
                val bundle = messages.baseBundleName
                throw MissingResourceException("Can''t find resource for bundle $bundle, key $message", bundle, message)
            } else {
                parent.getMessage(message, *args)
            }
        }
    }

    init {
        reload()
        localeProvider.locale().addPropertyChangeListener { reload() }
    }

}
