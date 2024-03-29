package gargoyle.ct.util.args

interface CTArgs : CTAnyArgs {
    fun getBoolean(index: Int): Boolean = getBoolean(index, false)
    fun getBoolean(key: String): Boolean = getBoolean(key, false)
    fun getBoolean(index: Int, def: Boolean): Boolean = get(index, def)
    fun getBoolean(key: String, def: Boolean): Boolean = get(key, def)

    fun getByte(index: Int): Byte = getByte(index, 0.toByte())
    fun getByte(key: String): Byte = getByte(key, 0.toByte())
    fun getByte(index: Int, def: Byte): Byte = get(index, def)
    fun getByte(key: String, def: Byte): Byte = get(key, def)

    fun getBytes(index: Int): ByteArray = getBytes(index, ByteArray(0))
    fun getBytes(key: String): ByteArray = getBytes(key, ByteArray(0))
    fun getBytes(index: Int, def: ByteArray): ByteArray =
        get(index, def)

    fun getBytes(key: String, def: ByteArray): ByteArray =
        get(key, def)

    fun getChar(index: Int): Char = getChar(index, '\u0000')
    fun getChar(key: String): Char = getChar(key, '\u0000')
    fun getChar(index: Int, def: Char): Char = get(index, def)
    fun getChar(key: String, def: Char): Char = get(key, def)

    fun getDouble(index: Int): Double = getDouble(index, 0.0)
    fun getDouble(key: String): Double = getDouble(key, 0.0)
    fun getDouble(index: Int, def: Double): Double = get(index, def)
    fun getDouble(key: String, def: Double): Double = get(key, def)

    fun getFloat(index: Int): Float = getFloat(index, 0.0f)
    fun getFloat(key: String): Float = getFloat(key, 0.0f)
    fun getFloat(index: Int, def: Float): Float = get(index, def)
    fun getFloat(key: String, def: Float): Float = get(key, def)

    fun getInteger(index: Int): Int = getInteger(index, 0)
    fun getInteger(key: String): Int = getInteger(key, 0)
    fun getInteger(index: Int, def: Int): Int = get(index, def)
    fun getInteger(key: String, def: Int): Int = get(key, def)

    fun getLong(index: Int): Long = getLong(index, 0L)
    fun getLong(key: String): Long = getLong(key, 0L)
    fun getLong(index: Int, def: Long): Long = get(index, def)
    fun getLong(key: String, def: Long): Long = get(key, def)

    fun getShort(index: Int): Short = getShort(index, 0.toShort())
    fun getShort(key: String): Short = getShort(key, 0.toShort())
    fun getShort(index: Int, def: Short): Short = get(index, def)
    fun getShort(key: String, def: Short): Short = get(key, def)

    fun getString(index: Int): String = getString(index, "")
    fun getString(key: String): String = getString(key, "")
    fun getString(index: Int, def: String): String = get(index, def)
    fun getString(key: String, def: String): String = get(key, def)
}
