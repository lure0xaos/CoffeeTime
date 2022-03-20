package gargoyle.ct.prop.impl.ro

import gargoyle.ct.prop.CTReadOnlyNumberProperty

class CTReadOnlyByteProperty(name: String, value: Byte) : CTBaseReadOnlyProperty<Byte>(name, value),
    CTReadOnlyNumberProperty<Byte>
