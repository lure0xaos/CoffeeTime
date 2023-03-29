package gargoyle.ct.util.messages.impl

import gargoyle.ct.util.messages.LocaleProvider
import gargoyle.ct.util.messages.findSimilar
import java.util.*

class CTFixedLocaleProvider(override var locale: Locale = Locale.getDefault().findSimilar()) : LocaleProvider
