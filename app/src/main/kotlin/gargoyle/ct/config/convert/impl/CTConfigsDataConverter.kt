package gargoyle.ct.config.convert.impl

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class CTConfigsDataConverter : KSerializer<List<LongArray>> {

    private val serializer: KSerializer<List<LongArray>> = ListSerializer(CTConfigDataConverter())

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): List<LongArray> = decoder.decodeSerializableValue(serializer)

    override fun serialize(encoder: Encoder, value: List<LongArray>) =
        encoder.encodeSerializableValue(serializer, value)

}
