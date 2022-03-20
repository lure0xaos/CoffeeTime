package gargoyle.ct.pref.impl.prop

import gargoyle.ct.convert.impl.SerializableConverter
import gargoyle.ct.pref.CTPreferencesProvider
import kotlin.reflect.KClass

class CTPrefSerializableProperty<T : Any> @JvmOverloads constructor(
    type: KClass<T>,
    provider: CTPreferencesProvider,
    name: String,
    def: T? = null
) : CTPrefProperty<T>(type, SerializableConverter(), provider, name, def)
