package gargoyle.ct.config.convert

import java.util.concurrent.TimeUnit

interface CTUnitConverter<T : Any> {
    fun format(unit: TimeUnit, data: T): String

    /**
     * @throws IllegalArgumentException on parse
     */
    fun parse(data: String): T
}
