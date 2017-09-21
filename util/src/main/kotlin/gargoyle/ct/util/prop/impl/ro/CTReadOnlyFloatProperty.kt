package gargoyle.ct.util.prop.impl.ro

import gargoyle.ct.util.prop.CTReadOnlyNumberProperty


class CTReadOnlyFloatProperty(name: String, value: Float) : CTBaseReadOnlyProperty<Float>(name, value),
    CTReadOnlyNumberProperty<Float>
