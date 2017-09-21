package gargoyle.ct.util.prop.impl.ro

import gargoyle.ct.util.prop.CTReadOnlyNumberProperty


class CTReadOnlyLongProperty(name: String, value: Long) : CTBaseReadOnlyProperty<Long>(name, value),
    CTReadOnlyNumberProperty<Long>
