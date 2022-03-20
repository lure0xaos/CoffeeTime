package gargoyle.ct.prop.impl.ro

import kotlin.reflect.KClass

class CTReadOnlyEnumProperty<E : Enum<E>> : CTBaseReadOnlyProperty<E> {
    constructor(type: KClass<E>, value: E) : super(type.qualifiedName!!.substringAfterLast('.'), value)
    constructor(name: String, value: E) : super(name, value)
    constructor(value: E) : super(value.javaClass.simpleName, value)
}
