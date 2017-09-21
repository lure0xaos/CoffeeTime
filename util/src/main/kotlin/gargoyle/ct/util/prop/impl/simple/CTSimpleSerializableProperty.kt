package gargoyle.ct.util.prop.impl.simple

import kotlin.reflect.KClass

class CTSimpleSerializableProperty<T : Any> : CTSimpleProperty<T> {
    constructor(type: KClass<T>, name: String) : super(type, name)
    constructor(name: String, def: T) : super(name, def)
}
