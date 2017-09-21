package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.convert.Converter
import gargoyle.ct.util.pref.CTPreferencesProvider
import kotlin.reflect.KClass


class CTPrefObjectProperty<T : Any>(
    type: KClass<T>,
    provider: CTPreferencesProvider,
    name: String,
    def: T?,
    converter: Converter<T>
) : CTPrefProperty<T>(type, converter, provider, name, def) {
    constructor(type: KClass<T>, provider: CTPreferencesProvider, name: String, converter: Converter<T>)
            : this(type, provider, name, null, converter)
}
