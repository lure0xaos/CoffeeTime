package gargoyle.ct.config

import java.io.InvalidObjectException
import java.io.Serial
import java.text.MessageFormat
import java.util.Collections
import java.util.LinkedList

open class CTConfigs : Any {
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
            val name = config.name
            if (!configs.containsKey(name)) {
                configs[name] = config
            }
        }
    }

    constructor(configs: List<CTConfig>) {
        setConfigs(configs)
    }

    fun getConfig(name: String?): CTConfig? {
        return configs[name]
    }

    fun getConfigs(): List<CTConfig> {
        return Collections.unmodifiableList(LinkedList(configs.values))
    }

    private fun setConfigs(configs: Iterable<CTConfig>) {
        synchronized(this.configs) {
            this.configs.clear()
            for (config in configs) {
                addConfig(config)
            }
        }
    }

    fun hasConfig(config: CTConfig): Boolean {
        return configs.containsKey(config.name)
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + configs.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val ctConfigs = other as CTConfigs
        return configs == ctConfigs.configs
    }

    override fun toString(): String {
        return MessageFormat.format("CTConfigs [configs={0}]", configs)
    }

    @Throws(InvalidObjectException::class)
    fun validate() {
        for (config in configs.values) {
            if (config.isNotValid) {
                throw InvalidObjectException(MessageFormat.format(MSG_NOT_VALID_CONVERT_0, config))
            }
        }
    }

    companion object {
        private const val MSG_NOT_VALID_CONVERT_0 = "not valid convert: {0}"

        @Serial
        private val serialVersionUID = 2024075953874239351L
    }
}
