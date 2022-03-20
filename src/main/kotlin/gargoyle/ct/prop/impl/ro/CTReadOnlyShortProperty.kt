package gargoyle.ct.prop.impl.ro

import gargoyle.ct.prop.CTReadOnlyNumberProperty

class CTReadOnlyShortProperty(name: String, value: Short) : CTBaseReadOnlyProperty<Short>(name, value),
    CTReadOnlyNumberProperty<Short>
