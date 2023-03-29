package gargoyle.ct.config.convert.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.CTConfigs
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.concurrent.TimeUnit

class CTConfigsConverter(val unit: TimeUnit = TimeUnit.MINUTES) : KSerializer<CTConfigs> {
    private val configConverter: KSerializer<List<CTConfig>> = ListSerializer(CTConfigConverter())

    override val descriptor: SerialDescriptor = configConverter.descriptor

    override fun deserialize(decoder: Decoder): CTConfigs =
        CTConfigs(configConverter.deserialize(decoder))

    override fun serialize(encoder: Encoder, value: CTConfigs) =
        configConverter.serialize(encoder, value.getConfigs())

}
