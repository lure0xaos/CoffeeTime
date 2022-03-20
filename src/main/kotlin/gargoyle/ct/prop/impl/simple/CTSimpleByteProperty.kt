package gargoyle.ct.prop.impl.simple

import gargoyle.ct.prop.CTNumberProperty

class CTSimpleByteProperty @JvmOverloads constructor(name: String, def: Byte = 0.toByte()) :
    CTSimpleProperty<Byte>(name, def), CTNumberProperty<Byte>
