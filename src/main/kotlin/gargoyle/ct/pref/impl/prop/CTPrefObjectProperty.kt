package gargoyle.ct.pref.impl.prop

import gargoyle.ct.convert.Converter
import gargoyle.ct.pref.CTPreferencesProvider
import kotlin.reflect.KClass

class CTPrefObjectProperty<T : Any>(
    type: KClass<T>,
    provider: CTPreferencesProvider,
    name: String,
    def: T?,
    converter: Converter<T>
) : CTPrefProperty<T>(type, converter, provider, name, def) {
    @Suppress("UNCHECKED_CAST")
    constructor(type: KClass<T>, provider: CTPreferencesProvider, name: String, converter: Converter<T>) : this(
        type,
        provider,
        name,
        null,
        converter
    )
}
