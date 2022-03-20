package gargoyle.ct.pref.impl.prop

import gargoyle.ct.convert.impl.EnumConverter
import gargoyle.ct.pref.CTPreferencesProvider
import kotlin.reflect.KClass

class CTPrefEnumProperty<E : Enum<E>> : CTPrefProperty<E> {
    constructor(provider: CTPreferencesProvider, type: KClass<E>, name: String) : super(
        type,
        EnumConverter<E>(type),
        provider,
        name,
        null
    )

    constructor(provider: CTPreferencesProvider, type: KClass<E>, name: String, def: E) : super(
        type,
        EnumConverter<E>(type),
        provider,
        name,
        def
    )

    constructor(provider: CTPreferencesProvider, type: KClass<E>) : super(
        type,
        EnumConverter<E>(type),
        provider,
        type.qualifiedName!!.substringAfterLast('.'),
        null
    )

    constructor(provider: CTPreferencesProvider, type: KClass<E>, def: E) : super(
        type,
        EnumConverter<E>(type),
        provider,
        type.qualifiedName!!.substringAfterLast('.'),
        def
    )
}
