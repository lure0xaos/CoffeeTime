package gargoyle.ct.util;

import java.io.IOException;
import java.net.ServerSocket;

public final class CTMutex {
    private static final int PORT = 34567;
    private static ServerSocket mutex;

    private CTMutex() {
    }

    public static synchronized boolean acquire() {
        try {
            mutex = new ServerSocket(PORT);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static synchronized void release() {
        try {
            if (mutex != null) {
                if (!mutex.isClosed()) {
                    mutex.close();
                }
                mutex = null;
            }
        } catch (IOException e) {
            // ignore
        }
    }
}
