package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.convert.Converter
import gargoyle.ct.util.convert.Converters
import gargoyle.ct.util.log.Log
import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.prop.impl.CTBaseObservableProperty
import java.util.prefs.BackingStoreException
import java.util.prefs.Preferences
import kotlin.reflect.KClass


open class CTPrefProperty<T : Any>
protected constructor(
    type: KClass<T>,
    converter: Converter<T>,
    provider: CTPreferencesProvider,
    name: String,
    def: T?
) : CTBaseObservableProperty<T>(type, name) {
    private val converter: Converter<T>
    private val def: String?
    private val preferences: Preferences

    protected constructor(type: KClass<T>, provider: CTPreferencesProvider, name: String, def: T) :
            this(type, Converters.get<T>(type), provider, name, def)

    init {
        this.converter = converter
        this.def = if (def == null) null else converter.format(def)
        preferences = provider.preferences()
    }

    override val isPresent: Boolean
        get() = preferences[name, null] != null

    override fun set(value: T) {
        val s = preferences[name, null]
        val oldValue: T? =(if(s==null) null else converter.parse(s ?: def ?: ""))
        if (oldValue != value) {
            preferences.put(name, converter.format(value)) //XXX
            sync()
            firePropertyChange(value, oldValue)
        }
    }

    override fun get(): T = converter.parse(preferences[name, null] ?: def ?: error("")) //XXX

    override fun get(def: T): T {
        val value: String = preferences[name, null] ?: return def
        return try {
            converter.parse(value)
        } catch (ex: IllegalArgumentException) {
            Log.warn(ex, ex.message ?: "")
            def
        }
    }

    private fun sync() {
        try {
            preferences.flush()
        } catch (ex: BackingStoreException) {
            Log.error(ex, ex.message ?: "")
        }
    }
}
