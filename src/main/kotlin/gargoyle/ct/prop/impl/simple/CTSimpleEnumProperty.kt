package gargoyle.ct.prop.impl.simple

import kotlin.reflect.KClass

class CTSimpleEnumProperty<E : Enum<E>> : CTSimpleProperty<E> {
    constructor(type: KClass<E>, name: String) : super(type, name)
    constructor(type: KClass<E>) : super(type, type.qualifiedName!!.substringAfterLast('.'))
    constructor(type: KClass<E>, def: E) : super(type.qualifiedName!!.substringAfterLast('.'), def)
    constructor(name: String, def: E) : super(name, def)
    constructor(def: E) : super(def.javaClass.simpleName, def)
}
