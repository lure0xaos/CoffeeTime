package gargoyle.ct.util

import gargoyle.ct.log.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.UnsupportedEncodingException
import java.util.Scanner

object CTStreamUtil {
    private const val DELIMITER = "\\A"
    fun convertStreamToString(stream: InputStream, charsetName: String): String {
        try {
            Scanner(stream, charsetName).use { scanner ->
                scanner.useDelimiter(DELIMITER).use { s -> return if (s.hasNext()) s.next() else "" }
            }
        } catch (ex: RuntimeException) {
            Log.error(ex, ex.message ?: "")
            throw ex
        }
    }

    fun write(stream: OutputStream, content: String, charsetName: String) {
        try {
            OutputStreamWriter(stream, charsetName).use { writer ->
                writer.write(content)
                writer.flush()
            }
        } catch (ex: UnsupportedEncodingException) {
            throw IllegalArgumentException(ex)
        } catch (ex: IOException) {
            throw ResourceException(ex.localizedMessage, ex)
        }
    }
}
