package gargoyle.ct.mutex;

import gargoyle.ct.log.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public final class FileMutex implements CTMutex {

    private final String           mutex;
    private       File             file;
    private       RandomAccessFile randomAccessFile;
    private       FileLock         fileLock;
    private       FileChannel      channel;

    public FileMutex(String mutex) {
        this.mutex = mutex;
    }

    @Override
    public synchronized boolean acquire() {
        try {
            file = new File(mutex);
            randomAccessFile = new RandomAccessFile(file, "rw");
            channel = randomAccessFile.getChannel();
            fileLock = channel.tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread(this::release));
                return true;
            }
        } catch (IOException ex) {
            // log.error("Unable to create and/or lock file: " + lockFile, e);
            Log.error(ex, MSG_MUTEX_ERROR);
        }
        return false;
    }

    @Override
    public synchronized void release() {
        if (file != null && file.exists() && fileLock != null && randomAccessFile != null) {
            try {
                fileLock.release();
                randomAccessFile.close();
                if (!file.delete()) {
                    throw new IOException(MSG_MUTEX_ERROR);
                }
                fileLock = null;
                randomAccessFile = null;
                file = null;
                channel = null;
            } catch (IOException ex) {
                Log.error(ex, MSG_MUTEX_ERROR);
            }
        }
    }
}
