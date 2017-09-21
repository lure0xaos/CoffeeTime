package gargoyle.ct.util.prop.impl.ro

import gargoyle.ct.util.prop.CTReadOnlyNumberProperty


class CTReadOnlyByteProperty(name: String, value: Byte) : CTBaseReadOnlyProperty<Byte>(name, value),
    CTReadOnlyNumberProperty<Byte>
