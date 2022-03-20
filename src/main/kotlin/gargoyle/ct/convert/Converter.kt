package gargoyle.ct.convert

interface Converter<T : Any> {
    fun format(data: T): String

    /**
     * @throws IllegalArgumentException on parse
     */
    fun parse(data: String): T
}
