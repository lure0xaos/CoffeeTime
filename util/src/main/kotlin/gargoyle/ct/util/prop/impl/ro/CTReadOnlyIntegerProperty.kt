package gargoyle.ct.util.prop.impl.ro

import gargoyle.ct.util.prop.CTReadOnlyNumberProperty


class CTReadOnlyIntegerProperty(name: String, value: Int) : CTBaseReadOnlyProperty<Int>(name, value),
    CTReadOnlyNumberProperty<Int>
