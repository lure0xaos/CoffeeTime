package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter

class ShortConverter : Converter<Short> {
    override fun format(data: Short): String {
        return data.toString()
    }

    override fun parse(data: String): Short {
        return data.toShort()
    }
}
