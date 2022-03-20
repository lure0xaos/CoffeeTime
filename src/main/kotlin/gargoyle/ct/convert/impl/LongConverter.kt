package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter

class LongConverter : Converter<Long> {
    override fun format(data: Long): String {
        return data.toString()
    }

    override fun parse(data: String): Long {
        return data.toLong()
    }
}
