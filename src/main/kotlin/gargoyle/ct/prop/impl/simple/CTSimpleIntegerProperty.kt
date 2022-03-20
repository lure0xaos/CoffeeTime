package gargoyle.ct.prop.impl.simple

import gargoyle.ct.prop.CTNumberProperty

class CTSimpleIntegerProperty @JvmOverloads constructor(name: String, def: Int = 0) :
    CTSimpleProperty<Int>(name, def), CTNumberProperty<Int>
