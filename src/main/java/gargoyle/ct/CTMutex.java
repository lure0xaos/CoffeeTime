package gargoyle.ct;

import java.io.IOException;
import java.net.ServerSocket;

public final class CTMutex {
	private static final int PORT = 34567;
	private static ServerSocket mutex;

	public static synchronized boolean acquire() {
		try {
			CTMutex.mutex = new ServerSocket(CTMutex.PORT);
			return true;
		} catch (final IOException ex) {
			return false;
		}
	}

	public static synchronized void release() {
		try {
			if (CTMutex.mutex != null) {
				if (!CTMutex.mutex.isClosed()) {
					CTMutex.mutex.close();
				}
				CTMutex.mutex = null;
			}
		} catch (final IOException e) {
			// ignore
		}
	}

	private CTMutex() {
		super();
	}
}
