package gargoyle.ct.mutex.impl

import gargoyle.ct.log.Log
import gargoyle.ct.mutex.CTMutex
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
                        TMPDIR,
                        System.getProperty(
                            USER_HOME,
                            System.getProperty(USER_DIR, ".")
                        )
                    )
                ),
                mutex + SUFFIX
            )
            randomAccessFile = RandomAccessFile(file, "rw")
            channel = randomAccessFile.channel
            fileLock = channel.tryLock()
            if (::fileLock.isInitialized) {
                Runtime.getRuntime().addShutdownHook(Thread { release() })
                file.deleteOnExit()
                return true
            }
        } catch (ex: IOException) {
            Log.error(ex, MSG_MUTEX_ERROR)
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
                if (!file.delete()) {
                    throw IOException(MSG_MUTEX_ERROR)
                }
//                fileLock = null
//                randomAccessFile = null
//                file = null
//                channel = null
            } catch (ex: IOException) {
                Log.error(ex, MSG_MUTEX_ERROR)
            }
        }
    }

    companion object {
        const val MSG_MUTEX_ERROR = "file mutex error"
        private const val SUFFIX = ".lock"
        private const val TMPDIR = "java.io.tmpdir"
        private const val USER_DIR = "user.dir"
        private const val USER_HOME = "user.home"
    }
}
