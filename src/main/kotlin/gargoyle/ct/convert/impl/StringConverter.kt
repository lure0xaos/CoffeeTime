package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter

class StringConverter : Converter<String> {
    override fun format(data: String): String {
        return data
    }

    override fun parse(data: String): String {
        return data
    }
}
