package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter

class FloatConverter : Converter<Float> {
    override fun format(data: Float): String {
        return data.toString()
    }

    override fun parse(data: String): Float {
        return data.toFloat()
    }
}
