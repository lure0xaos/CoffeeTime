package gargoyle.ct.convert.impl

import gargoyle.ct.convert.Converter
import gargoyle.ct.util.CTSerializationUtil
import java.io.IOException

class SerializableConverter<T : Any> : Converter<T> {
    private val converter = BytesConverter()
    override fun format(data: T): String {
        return try {
            converter.format(CTSerializationUtil.serialize(data))
        } catch (ex: IOException) {
            throw IllegalArgumentException(ex)
        }
    }

    override fun parse(data: String): T {
        return try {
            CTSerializationUtil.deserialize(converter.parse(data))
        } catch (ex: IOException) {
            throw IllegalArgumentException(data, ex)
        }
    }
}
