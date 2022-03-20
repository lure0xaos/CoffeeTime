package gargoyle.ct.prop.impl.simple

import gargoyle.ct.prop.CTNumberProperty

class CTSimpleLongProperty @JvmOverloads constructor(name: String, def: Long = 0L) :
    CTSimpleProperty<Long>(name, def), CTNumberProperty<Long>
