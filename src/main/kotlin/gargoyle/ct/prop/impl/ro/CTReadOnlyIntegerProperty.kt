package gargoyle.ct.prop.impl.ro

import gargoyle.ct.prop.CTReadOnlyNumberProperty

class CTReadOnlyIntegerProperty(name: String, value: Int) : CTBaseReadOnlyProperty<Int>(name, value),
    CTReadOnlyNumberProperty<Int>
