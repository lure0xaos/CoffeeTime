package gargoyle.ct.prop.impl.ro

import gargoyle.ct.prop.CTReadOnlyNumberProperty

class CTReadOnlyDoubleProperty(name: String, value: Double) : CTBaseReadOnlyProperty<Double>(name, value),
    CTReadOnlyNumberProperty<Double>
