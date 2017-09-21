package gargoyle.ct.util.prop.impl.simple

import gargoyle.ct.util.util.simpleClassName
import kotlin.reflect.KClass

class CTSimpleEnumProperty<E : Enum<E>> : CTSimpleProperty<E> {
    constructor(type: KClass<E>, name: String) : super(type, name)
    constructor(type: KClass<E>) : super(type, type.simpleClassName)
    constructor(type: KClass<E>, def: E) : super(type.simpleClassName, def)
    constructor(name: String, def: E) : super(name, def)
    constructor(def: E) : super(def::class.simpleClassName, def)
}
