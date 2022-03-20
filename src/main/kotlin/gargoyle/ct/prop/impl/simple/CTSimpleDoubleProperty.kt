package gargoyle.ct.prop.impl.simple

import gargoyle.ct.prop.CTNumberProperty

class CTSimpleDoubleProperty @JvmOverloads constructor(name: String, def: Double = 0.0) :
    CTSimpleProperty<Double>(name, def), CTNumberProperty<Double>
