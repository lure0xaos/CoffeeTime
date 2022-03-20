package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter
import kotlin.reflect.KClass

class EnumConverter<E : Enum<E>>(private val type: KClass<E>) : Converter<E> {
    override fun format(data: E): String {
        return data.name
    }

    override fun parse(data: String): E {
        return type.java.enumConstants.first { it.name == data }
    }
}
