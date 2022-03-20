package gargoyle.ct.prop.impl.ro

import gargoyle.ct.prop.CTReadOnlyNumberProperty

class CTReadOnlyLongProperty(name: String, value: Long) : CTBaseReadOnlyProperty<Long>(name, value),
    CTReadOnlyNumberProperty<Long>
