package gargoyle.ct.config.convert.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.CTConfigs
import gargoyle.ct.config.convert.CTUnitConverter
import gargoyle.ct.util.log.Log
import java.util.concurrent.TimeUnit

class CTConfigsConverter : CTUnitConverter<CTConfigs> {
    private val configConverter: CTUnitConverter<CTConfig> = CTConfigConverter()
    private val configsDataConverter = CTConfigsDataConverter()
    override fun format(unit: TimeUnit, data: CTConfigs): String =
        configsDataConverter.format(unit, data.getConfigs().map { configConverter.format(unit, it) }.toTypedArray())

    override fun parse(data: String): CTConfigs =
        CTConfigs(configsDataConverter.parse(data).filter { it.isNotEmpty() }.mapNotNull {
            try {
                configConverter.parse(it)
            } catch (ex: IllegalArgumentException) {
                Log.error("skip invalid convert line: $it")
                null
            }
        })

}
