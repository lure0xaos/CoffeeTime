package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter

class IntegerConverter : Converter<Int> {
    override fun format(data: Int): String {
        return data.toString()
    }

    override fun parse(data: String): Int {
        return data.toInt()
    }
}
