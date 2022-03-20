package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter

class DoubleConverter : Converter<Double> {
    override fun format(data: Double): String {
        return data.toString()
    }

    override fun parse(data: String): Double {
        return data.toDouble()
    }
}
