package gargoyle.ct.pref.impl.prop

import gargoyle.ct.pref.CTPreferencesProvider
import gargoyle.ct.prop.CTNumberProperty

class CTPrefByteProperty @JvmOverloads constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Byte = 0.toByte()
) : CTPrefProperty<Byte>(
    Byte::class, provider, name, def
), CTNumberProperty<Byte>
