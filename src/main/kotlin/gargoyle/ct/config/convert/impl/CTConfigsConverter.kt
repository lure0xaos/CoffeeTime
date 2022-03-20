package gargoyle.ct.config.convert.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.CTConfigs
import gargoyle.ct.config.convert.CTUnitConverter
import gargoyle.ct.log.Log
import org.jetbrains.annotations.Contract
import java.util.concurrent.TimeUnit

class CTConfigsConverter : CTUnitConverter<CTConfigs> {
    private val configConverter: CTUnitConverter<CTConfig> = CTConfigConverter()
    private val configsDataConverter = CTConfigsDataConverter()
    override fun format(unit: TimeUnit, data: CTConfigs): String {
        return configsDataConverter.format(unit, data.getConfigs().map {
            configConverter.format(unit, it)
        }.toTypedArray())
    }

    @Contract("_ -> new")
    override fun parse(data: String): CTConfigs {
        return CTConfigs(configsDataConverter.parse(data).filter { it.isNotEmpty() }.mapNotNull {
            try {
                configConverter.parse(it)
            } catch (ex: IllegalArgumentException) {
                Log.error(MSG_INVALID_CONVERT_LINE_0, it)
                null
            }
        })
    }

    companion object {
        private const val MSG_INVALID_CONVERT_LINE_0 = "skip invalid convert line: {0}"
    }
}
