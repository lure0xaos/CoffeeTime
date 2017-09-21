package gargoyle.ct.preferences.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.convert.CTUnitConverter
import gargoyle.ct.config.convert.impl.CTConfigConverter
import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.preferences.CTPreferences.IconStyle
import gargoyle.ct.util.convert.Converter
import gargoyle.ct.util.messages.SupportedLocales
import gargoyle.ct.util.messages.SupportedLocales.Companion.findSimilar
import gargoyle.ct.util.pref.CTPropertyChangeEvent
import gargoyle.ct.util.pref.impl.CTBasePreferences
import gargoyle.ct.util.pref.prop.*
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class CTPreferencesImpl(clazz: KClass<*>) : CTBasePreferences(clazz), CTPreferences {
    init {
        addProperty(CTPrefBooleanProperty(this, CTPreferences.PREF_BLOCK, false))
        val transparencyLevel: CTPrefProperty<Int> =
            CTPrefIntegerProperty(this, CTPreferences.PREF_TRANSPARENCY_LEVEL, 30)
        addProperty(transparencyLevel)
        transparencyLevel.addPropertyChangeListener { event: CTPropertyChangeEvent<Int> ->
            if (event.newValue < 1) event.property.set(1)
        }
        addProperty(
            CTPrefEnumProperty(
                this,
                IconStyle::class,
                CTPreferences.PREF_ICON_STYLE,
                IconStyle.BW
            )
        )
        addProperty(CTPrefBooleanProperty(this, CTPreferences.PREF_SOUND, true))
        addProperty(CTPrefBooleanProperty(this, CTPreferences.PREF_TRANSPARENCY, true))
        addProperty(CTPrefEnumProperty(this, SupportedLocales::class, findSimilar()))
        addProperty(
            CTPrefObjectProperty(
                CTConfig::class,
                this,
                CTPreferences.PREF_CONFIG,
                ConfigConverterAdapter()
            )
        )
    }

    override fun block(): CTPrefProperty<Boolean> {
        return getProperty(CTPreferences.PREF_BLOCK)
    }

    override fun config(): CTPrefProperty<CTConfig> {
        return getProperty(CTPreferences.PREF_CONFIG)
    }

    override fun iconStyle(): CTPrefProperty<IconStyle> {
        return getProperty(CTPreferences.PREF_ICON_STYLE)
    }

    override fun supportedLocales(): CTPrefProperty<SupportedLocales> {
        return getProperty(SupportedLocales::class)
    }

    override fun sound(): CTPrefProperty<Boolean> {
        return getProperty(CTPreferences.PREF_SOUND)
    }

    override fun transparency(): CTPrefProperty<Boolean> {
        return getProperty(CTPreferences.PREF_TRANSPARENCY)
    }

    override fun transparencyLevel(): CTPrefProperty<Int> {
        return getProperty(CTPreferences.PREF_TRANSPARENCY_LEVEL)
    }

    private class ConfigConverterAdapter : Converter<CTConfig> {
        private val converter: CTUnitConverter<CTConfig> = CTConfigConverter()
        override fun format(data: CTConfig): String {
            return converter.format(TimeUnit.MINUTES, data)
        }

        override fun parse(data: String): CTConfig {
            return converter.parse(data)
        }
    }
}
