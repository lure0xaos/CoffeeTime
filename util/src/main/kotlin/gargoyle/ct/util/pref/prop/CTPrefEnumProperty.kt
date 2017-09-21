package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.convert.Converter
import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.util.simpleClassName
import kotlin.reflect.KClass

class CTPrefEnumProperty<E : Enum<E>> : CTPrefProperty<E> {
    constructor(provider: CTPreferencesProvider, type: KClass<E>, name: String) :
            super(type, EnumConverter<E>(type), provider, name, null)

    constructor(provider: CTPreferencesProvider, type: KClass<E>, name: String, def: E) :
            super(type, EnumConverter<E>(type), provider, name, def)

    constructor(provider: CTPreferencesProvider, type: KClass<E>) :
            super(type, EnumConverter<E>(type), provider, type.simpleClassName, null)

    constructor(provider: CTPreferencesProvider, type: KClass<E>, def: E) :
            super(type, EnumConverter<E>(type), provider, type.simpleClassName, def)

    class EnumConverter<E : Enum<E>>(private val type: KClass<E>) : Converter<E> {
        override fun format(data: E): String = data.name

        override fun parse(data: String): E = type.java.enumConstants.first { it.name == data }
    }
}
