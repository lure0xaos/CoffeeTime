package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter

class ByteConverter : Converter<Byte> {
    override fun format(data: Byte): String {
        return data.toString()
    }

    override fun parse(data: String): Byte {
        return data.toByte()
    }
}
