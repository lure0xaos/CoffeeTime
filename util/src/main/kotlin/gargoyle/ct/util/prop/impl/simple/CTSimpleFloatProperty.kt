package gargoyle.ct.util.prop.impl.simple

import gargoyle.ct.util.prop.CTNumberProperty


class CTSimpleFloatProperty constructor(name: String, def: Float = 0f) :
    CTSimpleProperty<Float>(name, def), CTNumberProperty<Float>
