package gargoyle.ct.util.util

import java.io.*

object CTSerializationUtil {
    /**
     * pipe object through serialization
     *
     * @param orig object
     * @param <T>  type
     * @return - same object
     * @throws IOException any other error
    </T> */
    fun <T> pipe(orig: T): T {
        val serialized = serialize(orig)
        return deserialize(serialized)
    }

    /**
     * deserialize object using native serialization
     *
     * @param bytes - source
     * @param <T>   - target type
     * @return - deserialized object
     * @throws IOException any other error
     * @see ObjectInput.readObject
    </T> */
    fun <T : Any> deserialize(bytes: ByteArray): T {
        ObjectInputStream(ByteArrayInputStream(bytes)).use { ois ->
            return try {
                @Suppress("UNCHECKED_CAST")
                ois.readObject() as T
            } catch (e: ClassNotFoundException) {
                throw IOException(e.localizedMessage, e)
            }
        }
    }

    /**
     * serialize object using native serialization
     *
     * @param o   - source object
     * @param <T> type of object
     * @return - serialized byte array
     * @throws IOException any i*/
    fun <T> serialize(o: T): ByteArray {
        ByteArrayOutputStream().use { out ->
            ObjectOutputStream(out).use { oos ->
                oos.writeObject(o)
                oos.flush()
                return out.toByteArray()
            }
        }
    }
}
