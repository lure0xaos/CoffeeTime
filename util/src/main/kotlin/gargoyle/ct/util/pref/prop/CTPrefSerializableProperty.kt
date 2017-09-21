package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.convert.Converter
import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.util.CTSerializationUtil
import java.io.IOException
import java.util.*
import kotlin.reflect.KClass


class CTPrefSerializableProperty<T : Any> constructor(
    type: KClass<T>,
    provider: CTPreferencesProvider,
    name: String,
    def: T? = null
) : CTPrefProperty<T>(type, object : Converter<T> {

    override fun format(data: T): String =
        try {
            Base64.getEncoder().encodeToString(CTSerializationUtil.serialize(data))
        } catch (ex: IOException) {
            throw IllegalArgumentException(ex)
        }

    override fun parse(data: String): T =
        try {
            CTSerializationUtil.deserialize(Base64.getDecoder().decode(data))
        } catch (ex: IOException) {
            throw IllegalArgumentException(data, ex)
        }
}, provider, name, def)
