package gargoyle.ct.config.convert.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.convert.CTUnitConverter
import java.util.concurrent.TimeUnit

class CTConfigConverter : CTUnitConverter<CTConfig> {
    private val configDataConverter = CTConfigDataConverter()
    override fun format(unit: TimeUnit, data: CTConfig): String {
        return configDataConverter.format(TimeUnit.MINUTES, data.whole, data.block, data.warn)
    }

    override fun parse(data: String): CTConfig {
        val parsed = configDataConverter.parse(data)
        return CTConfig(parsed[0], parsed[1], parsed[2])
    }
}
