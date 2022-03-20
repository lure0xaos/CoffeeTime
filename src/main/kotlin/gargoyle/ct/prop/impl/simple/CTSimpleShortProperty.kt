package gargoyle.ct.prop.impl.simple

import gargoyle.ct.prop.CTNumberProperty

class CTSimpleShortProperty @JvmOverloads constructor(name: String, def: Short = 0.toShort()) :
    CTSimpleProperty<Short>(name, def), CTNumberProperty<Short>
