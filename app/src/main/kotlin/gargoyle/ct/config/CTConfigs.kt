package gargoyle.ct.config

import gargoyle.ct.config.convert.impl.CTConfigsConverter
import kotlinx.serialization.Serializable
import java.text.MessageFormat
import java.util.*

@Serializable(with = CTConfigsConverter::class)
open class CTConfigs {
    private val configs: MutableMap<String, CTConfig> = LinkedHashMap()

    constructor(vararg configs: CTConfig) {
        setConfigs(arrayOf(*configs))
    }

    private fun setConfigs(configs: Array<CTConfig>) {
        synchronized(this.configs) {
            this.configs.clear()
            for (config in configs) {
                addConfig(config)
            }
        }
    }

    fun addConfig(config: CTConfig) {
        synchronized(configs) {
            if ((config.name) !in configs) configs[config.name] = config
        }
    }

    constructor(configs: List<CTConfig>) {
        setConfigs(configs)
    }

    fun getConfig(name: String): CTConfig? = configs[name]

    fun getConfigs(): List<CTConfig> = LinkedList(configs.values)

    private fun setConfigs(configs: Iterable<CTConfig>) {
        synchronized(this.configs) {
            this.configs.clear()
            configs.forEach { addConfig(it) }
        }
    }

    fun hasConfig(config: CTConfig): Boolean = configs.containsKey(config.name)

    override fun hashCode(): Int = 31 * 1 + configs.hashCode()

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other == null -> false
            javaClass != other.javaClass -> false
            else -> configs == (other as CTConfigs).configs
        }

    override fun toString(): String = MessageFormat.format("CTConfigs [configs={0}]", configs)

    fun validate() {
        configs.values.first { !it.isValid }
    }

    companion object {
        private const val MSG_NOT_VALID_CONVERT_0 = "not valid convert: {0}"
    }
}
