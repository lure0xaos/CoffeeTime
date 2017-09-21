package gargoyle.ct.util.prop.impl.simple

import gargoyle.ct.util.prop.CTNumberProperty


class CTSimpleIntegerProperty constructor(name: String, def: Int = 0) :
    CTSimpleProperty<Int>(name, def), CTNumberProperty<Int>
