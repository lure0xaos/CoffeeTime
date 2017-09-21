package gargoyle.ct.util.mutex.impl

import gargoyle.ct.util.log.Log
import gargoyle.ct.util.mutex.CTMutex
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.channels.FileLock
import java.nio.channels.OverlappingFileLockException

class FileMutex(private val mutex: String) : CTMutex {
    private lateinit var channel: FileChannel
    private lateinit var file: File
    private lateinit var fileLock: FileLock
    private lateinit var randomAccessFile: RandomAccessFile

    @Synchronized
    override fun acquire(): Boolean {
        if (::file.isInitialized && file.exists()) {
            return false
        }
        try {
            file = File(
                File(
                    System.getProperty(
                        "java.io.tmpdir",
                        System.getProperty("user.home", System.getProperty("user.dir", "."))
                    )
                ),
                "$mutex.lock"
            )
            randomAccessFile = RandomAccessFile(file, "rw")
            channel = randomAccessFile.channel
            channel.tryLock()?.also { fileLock = it }
            if (::fileLock.isInitialized) {
                Runtime.getRuntime().addShutdownHook(Thread { release() })
                file.deleteOnExit()
                return true
            }
        } catch (ex: IOException) {
            Log.error(ex, "file mutex error")
            return false
        } catch (ex: OverlappingFileLockException) {
            return false
        }
        return false
    }

    @Synchronized
    override fun release() {
        if (::file.isInitialized && file.exists()) {
            try {
                fileLock.release()
                randomAccessFile.close()
                if (!file.delete()) throw IOException("file mutex error")
//                fileLock = null
//                randomAccessFile = null
//                file = null
//                channel = null
            } catch (ex: IOException) {
                Log.error(ex, "file mutex error")
            }
        }
    }

}
