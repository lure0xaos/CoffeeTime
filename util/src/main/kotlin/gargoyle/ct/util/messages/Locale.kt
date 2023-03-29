package gargoyle.ct.util.messages

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

val locales = listOf(Locale.ENGLISH, Locale("ru", "RU"))

val Locale.description: String get() = displayName


fun Locale.findSimilar(def: Locale = Locale.ENGLISH): Locale =
    locales.firstOrNull { value: Locale -> value == (this) }
        ?: locales.firstOrNull { value: Locale -> value.language == this.language }
        ?: def

class LocaleSerializer : KSerializer<Locale> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Locale", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Locale {
        return Locale(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Locale) {
        encoder.encodeString(value.toString())
    }
}
