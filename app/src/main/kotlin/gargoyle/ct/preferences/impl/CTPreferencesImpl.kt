package gargoyle.ct.preferences.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.CTStandardConfigs
import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.preferences.CTPreferences.IconStyle
import gargoyle.ct.util.messages.LocaleSerializer
import gargoyle.ct.util.messages.findSimilar
import gargoyle.ct.util.pref.PreferencesObservableDelegate
import java.util.*
import kotlin.reflect.KClass

class CTPreferencesImpl(clazz: KClass<*>) : CTPreferences {

    override var block: Boolean by PreferencesObservableDelegate(clazz, false)

    override var config: CTConfig by PreferencesObservableDelegate(clazz, CTStandardConfigs.getConfigs().first())

    override var iconStyle: IconStyle by PreferencesObservableDelegate(clazz, IconStyle.BW)

    override var supportedLocales: Locale by PreferencesObservableDelegate(
        clazz,
        Locale.getDefault().findSimilar(),
        serializer = LocaleSerializer()
    )

    override var sound: Boolean by PreferencesObservableDelegate(clazz, true)

    override var transparency: Boolean by PreferencesObservableDelegate(clazz, true)

    override var transparencyLevel: Int by PreferencesObservableDelegate(
        clazz,
        30,
        beforeChange = { _, _, newValue -> newValue >= 1.0 })

}
