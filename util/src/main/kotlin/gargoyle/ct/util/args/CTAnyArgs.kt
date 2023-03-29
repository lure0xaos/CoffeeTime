package gargoyle.ct.util.args

interface CTAnyArgs {
    operator fun <T : Any> get(index: Int, def: T): T = get(name(index), def)
    operator fun <T : Any> get(key: String, def: T): T
    fun has(index: Int): Boolean = index in (0 until size())
    fun has(key: String): Boolean
    fun size(): Int

    fun name(index: Int): String
}
