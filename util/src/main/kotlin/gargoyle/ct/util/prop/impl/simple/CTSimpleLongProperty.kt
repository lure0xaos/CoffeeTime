package gargoyle.ct.util.prop.impl.simple

import gargoyle.ct.util.prop.CTNumberProperty


class CTSimpleLongProperty constructor(name: String, def: Long = 0L) :
    CTSimpleProperty<Long>(name, def), CTNumberProperty<Long>
