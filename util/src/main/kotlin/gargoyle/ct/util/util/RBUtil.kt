package gargoyle.ct.util.util

import java.util.*

operator fun ResourceBundle.get(key: String, def: String? = null): String? =
    if (containsKey(key)) getString(key) else def
