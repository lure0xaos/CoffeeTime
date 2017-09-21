package gargoyle.ct.util.prop.impl.simple

import gargoyle.ct.util.prop.CTNumberProperty


class CTSimpleShortProperty constructor(name: String, def: Short = 0.toShort()) :
    CTSimpleProperty<Short>(name, def), CTNumberProperty<Short>
