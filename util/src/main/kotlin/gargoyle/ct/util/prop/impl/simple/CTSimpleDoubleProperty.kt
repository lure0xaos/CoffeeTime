package gargoyle.ct.util.prop.impl.simple

import gargoyle.ct.util.prop.CTNumberProperty


class CTSimpleDoubleProperty constructor(name: String, def: Double = 0.0) :
    CTSimpleProperty<Double>(name, def), CTNumberProperty<Double>
