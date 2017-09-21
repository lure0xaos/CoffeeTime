package gargoyle.ct.util.util

import java.io.InputStream
import java.io.OutputStream

object CTStreamUtil {
    fun convertStreamToString(stream: InputStream, charsetName: String): String =
        stream.reader(charset(charsetName)).use { it.readText() }

    fun write(stream: OutputStream, content: String, charsetName: String): Unit =
        stream.writer(charset(charsetName)).use { it.write(content) }
}
