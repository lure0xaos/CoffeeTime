package gargoyle.ct.config.convert.impl

import gargoyle.ct.config.convert.CTUnitConverter
import java.util.concurrent.TimeUnit

class CTConfigsDataConverter : CTUnitConverter<Array<String>> {
    override fun format(unit: TimeUnit, data: Array<String>): String {
        val ret = StringBuilder()
        for (string in data) {
            ret.append(string).append(NEWLINE)
        }
        return ret.toString()
    }

    override fun parse(data: String): Array<String> {
        return data.split(NEWLINE).toTypedArray()
    }

    companion object {
        private const val ENV_LINE_SEPARATOR = "line.separator"
        private val NEWLINE = System.getProperty(ENV_LINE_SEPARATOR, "\n")
    }
}
