package gargoyle.ct.prop.impl.ro

import gargoyle.ct.prop.CTReadOnlyNumberProperty

class CTReadOnlyFloatProperty(name: String, value: Float) : CTBaseReadOnlyProperty<Float>(name, value),
    CTReadOnlyNumberProperty<Float>
