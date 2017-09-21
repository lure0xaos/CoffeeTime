package gargoyle.ct.config.convert.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.convert.CTUnitConverter
import java.util.concurrent.TimeUnit

class CTConfigConverter : CTUnitConverter<CTConfig> {
    private val configDataConverter = CTConfigDataConverter()
    override fun format(unit: TimeUnit, data: CTConfig): String =
        configDataConverter.format(TimeUnit.MINUTES, data.whole, data.block, data.warn)

    override fun parse(data: String): CTConfig = configDataConverter.parse(data).let { CTConfig(it[0], it[1], it[2]) }
}
