package gargoyle.ct.mutex.impl

import gargoyle.ct.log.Log
import gargoyle.ct.mutex.CTMutex
import java.io.IOException
import java.net.ServerSocket

class SocketMutex(private val port: Int) : CTMutex {
    private lateinit var mutex: ServerSocket

    @Synchronized
    override fun acquire(): Boolean {
        return try {
            mutex = ServerSocket(port)
            true
        } catch (ex: IOException) {
            false
        }
    }

    @Synchronized
    override fun release() {
        try {
            if (::mutex.isInitialized) {
                if (!mutex.isClosed) {
                    mutex.close()
                }
//                mutex = null
            }
        } catch (ex: IOException) {
            Log.error(ex, MSG_MUTEX_ERROR)
        }
    }

    companion object {
        const val MSG_MUTEX_ERROR = "socket mutex error"
    }
}
