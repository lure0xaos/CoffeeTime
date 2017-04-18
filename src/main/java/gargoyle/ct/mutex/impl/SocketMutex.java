package gargoyle.ct.mutex.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.mutex.CTMutex;

import java.io.IOException;
import java.net.ServerSocket;

public final class SocketMutex implements CTMutex {

    private final int          port;
    private       ServerSocket mutex;

    public SocketMutex(int port) {
        this.port = port;
    }

    @Override
    public synchronized boolean acquire() {
        try {
            mutex = new ServerSocket(port);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
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
