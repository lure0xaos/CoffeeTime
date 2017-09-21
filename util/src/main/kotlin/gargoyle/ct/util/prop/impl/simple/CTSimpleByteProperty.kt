package gargoyle.ct.util.prop.impl.simple

import gargoyle.ct.util.prop.CTNumberProperty


class CTSimpleByteProperty constructor(name: String, def: Byte = 0.toByte()) :
    CTSimpleProperty<Byte>(name, def), CTNumberProperty<Byte>
