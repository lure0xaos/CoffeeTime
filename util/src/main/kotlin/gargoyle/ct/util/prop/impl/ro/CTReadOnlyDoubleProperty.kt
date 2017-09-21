package gargoyle.ct.util.prop.impl.ro

import gargoyle.ct.util.prop.CTReadOnlyNumberProperty


class CTReadOnlyDoubleProperty(name: String, value: Double) : CTBaseReadOnlyProperty<Double>(name, value),
    CTReadOnlyNumberProperty<Double>
