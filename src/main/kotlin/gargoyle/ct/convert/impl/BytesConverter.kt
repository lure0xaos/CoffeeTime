package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter
import java.util.Base64

class BytesConverter : Converter<ByteArray> {
    override fun format(data: ByteArray): String {
        return Base64.getEncoder().encodeToString(data)
    }

    override fun parse(data: String): ByteArray {
        return Base64.getDecoder().decode(data)
    }
}
