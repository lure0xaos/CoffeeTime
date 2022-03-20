package gargoyle.ct.prop.impl.simple

import gargoyle.ct.prop.CTNumberProperty

class CTSimpleFloatProperty @JvmOverloads constructor(name: String, def: Float = 0f) :
    CTSimpleProperty<Float>(name, def), CTNumberProperty<Float>
