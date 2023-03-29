package gargoyle.ct.util.args.impl

import gargoyle.ct.util.args.CTArgs
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

open class CTArgsImpl(args: Array<String>) : CTArgs {
    private val params: LinkedHashMap<String, String>

    init {
        params = args.associateTo(LinkedHashMap()) { arg ->
            if (arg.contains('=')) {
                arg.substringBefore('=').trimStart() to arg.substringAfter('=').trimEnd()
            } else {
                arg.trim().let { it to it }
            }
        }
    }

    @OptIn(InternalSerializationApi::class)
    override fun <T : Any> get(key: String, def: T): T = Json.decodeFromString(def::class.serializer(), key)

    override fun has(key: String): Boolean = params.containsKey(key)

    override fun size(): Int = params.size
    override fun name(index: Int): String {
        params.onEachIndexed { idx, entry ->
            if (idx == index) return entry.key
        }
        error("not found")
    }


}
