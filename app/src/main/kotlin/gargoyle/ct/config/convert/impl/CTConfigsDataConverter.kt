package gargoyle.ct.config.convert.impl

import gargoyle.ct.config.convert.CTUnitConverter
import java.util.concurrent.TimeUnit

class CTConfigsDataConverter : CTUnitConverter<Array<String>> {
    override fun format(unit: TimeUnit, data: Array<String>): String = data.joinToString(NEWLINE)

    override fun parse(data: String): Array<String> = data.split(NEWLINE).toTypedArray()

    companion object {
        private val NEWLINE = System.getProperty("line.separator", "\n")
    }
}
