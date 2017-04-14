package gargoyle.ct.mutex;

import gargoyle.ct.log.Log;

import java.io.IOException;
import java.net.ServerSocket;

public final class SocketMutex {

    private static final String MSG_MUTEX_ERROR = "mutex error";
    private static final int    PORT            = 34567;
    private static SocketMutex  defaultMutex;
    private final  int          port;
    private        ServerSocket mutex;

    private SocketMutex(int port) {
        this.port = port;
    }

    public static SocketMutex getDefault() {
        if (defaultMutex == null) {
            synchronized (SocketMutex.class) {
                if (defaultMutex == null) {
                    defaultMutex = new SocketMutex(PORT);
                }
            }
        }
        return defaultMutex;
    }

    public synchronized boolean acquire() {
        try {
            mutex = new ServerSocket(port);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public synchronized void release() {
        try {
            if (mutex != null) {
                if (!mutex.isClosed()) {
                    mutex.close();
                }
                mutex = null;
            }
        } catch (IOException ex) {
            Log.error(ex, MSG_MUTEX_ERROR);
        }
    }
}
