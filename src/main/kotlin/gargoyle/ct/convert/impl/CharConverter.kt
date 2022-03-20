package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter

class CharConverter : Converter<Char> {
    override fun format(data: Char): String {
        return data.toString()
    }

    override fun parse(data: String): Char {
        return data[0]
    }
}
