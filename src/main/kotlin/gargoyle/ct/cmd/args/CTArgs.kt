package gargoyle.ct.cmd.args

import gargoyle.ct.convert.Converters

interface CTArgs : CTAnyArgs {
    fun getBoolean(index: Int): Boolean {
        return getBoolean(index, false)
    }

    fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    fun getBoolean(index: Int, def: Boolean): Boolean {
        return get(index, Boolean::class, Converters[Boolean::class], def)
    }

    fun getBoolean(key: String, def: Boolean): Boolean {
        return get(key, Boolean::class, Converters[Boolean::class], def)
    }

    fun getByte(index: Int): Byte {
        return getByte(index, 0.toByte())
    }

    fun getByte(key: String): Byte {
        return getByte(key, 0.toByte())
    }

    fun getByte(index: Int, def: Byte): Byte {
        return get(index, Byte::class, Converters[Byte::class], def)
    }

    fun getByte(key: String, def: Byte): Byte {
        return get(key, Byte::class, Converters[Byte::class], def)
    }

    fun getBytes(index: Int): ByteArray {
        return getBytes(index, ByteArray(0))
    }

    fun getBytes(key: String): ByteArray {
        return getBytes(key, ByteArray(0))
    }

    fun getBytes(index: Int, def: ByteArray): ByteArray {
        return get(index, ByteArray::class, Converters[ByteArray::class], def)
    }

    fun getBytes(key: String, def: ByteArray): ByteArray {
        return get(key, ByteArray::class, Converters[ByteArray::class], def)
    }

    fun getChar(index: Int): Char {
        return getChar(index, '\u0000')
    }

    fun getChar(key: String): Char {
        return getChar(key, '\u0000')
    }

    fun getChar(index: Int, def: Char): Char {
        return get(index, Char::class, Converters[Char::class], def)
    }

    fun getChar(key: String, def: Char): Char {
        return get(key, Char::class, Converters[Char::class], def)
    }

    fun getDouble(index: Int): Double {
        return getDouble(index, 0.0)
    }

    fun getDouble(key: String): Double {
        return getDouble(key, 0.0)
    }

    fun getDouble(index: Int, def: Double): Double {
        return get(index, Double::class, Converters[Double::class], def)
    }

    fun getDouble(key: String, def: Double): Double {
        return get(key, Double::class, Converters[Double::class], def)
    }

    fun getFloat(index: Int): Float {
        return getFloat(index, 0.0f)
    }

    fun getFloat(key: String): Float {
        return getFloat(key, 0.0f)
    }

    fun getFloat(index: Int, def: Float): Float {
        return get(index, Float::class, Converters[Float::class], def)
    }

    fun getFloat(key: String, def: Float): Float {
        return get(key, Float::class, Converters[Float::class], def)
    }

    fun getInteger(index: Int): Int {
        return getInteger(index, 0)
    }

    fun getInteger(key: String): Int {
        return getInteger(key, 0)
    }

    fun getInteger(index: Int, def: Int): Int {
        return get(index, Int::class, Converters[Int::class], def)
    }

    fun getInteger(key: String, def: Int): Int {
        return get(key, Int::class, Converters[Int::class], def)
    }

    fun getLong(index: Int): Long {
        return getLong(index, 0L)
    }

    fun getLong(key: String): Long {
        return getLong(key, 0L)
    }

    fun getLong(index: Int, def: Long): Long {
        return get(index, Long::class, Converters[Long::class], def)
    }

    fun getLong(key: String, def: Long): Long {
        return get(key, Long::class, Converters[Long::class], def)
    }

    fun getShort(index: Int): Short {
        return getShort(index, 0.toShort())
    }

    fun getShort(key: String): Short {
        return getShort(key, 0.toShort())
    }

    fun getShort(index: Int, def: Short): Short {
        return get(index, Short::class, Converters[Short::class], def)
    }

    fun getShort(key: String, def: Short): Short {
        return get(key, Short::class, Converters[Short::class], def)
    }

    fun getString(index: Int): String {
        return getString(index, "")
    }

    fun getString(key: String): String {
        return getString(key, "")
    }

    fun getString(index: Int, def: String): String {
        return get(index, String::class, Converters[String::class], def)
    }

    fun getString(key: String, def: String): String {
        return get(key, String::class, Converters[String::class], def)
    }
}
