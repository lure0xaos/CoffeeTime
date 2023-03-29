package gargoyle.ct.config.convert.impl

import gargoyle.ct.config.CTConfig
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.concurrent.TimeUnit

class CTConfigConverter : KSerializer<CTConfig> {
    private val configDataConverter = CTConfigDataConverter(TimeUnit.MINUTES)

    override val descriptor: SerialDescriptor = configDataConverter.descriptor

    override fun deserialize(decoder: Decoder): CTConfig =
        configDataConverter.deserialize(decoder).let { CTConfig(it[0], it[1], it[2]) }

    override fun serialize(encoder: Encoder, value: CTConfig) =
        configDataConverter.serialize(encoder, longArrayOf(value.whole, value.block, value.warn))
}
