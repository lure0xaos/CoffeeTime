package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter

class BooleanConverter : Converter<Boolean> {
    override fun format(data: Boolean): String {
        return data.toString()
    }

    override fun parse(data: String): Boolean {
        return data.toBoolean()
    }
}
