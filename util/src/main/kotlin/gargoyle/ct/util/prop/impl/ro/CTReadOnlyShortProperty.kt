package gargoyle.ct.util.prop.impl.ro

import gargoyle.ct.util.prop.CTReadOnlyNumberProperty


class CTReadOnlyShortProperty(name: String, value: Short) : CTBaseReadOnlyProperty<Short>(name, value),
    CTReadOnlyNumberProperty<Short>
