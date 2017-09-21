package gargoyle.ct.mutex;

public interface CTMutex {
    String MSG_MUTEX_ERROR = "mutex error";

    boolean acquire();

    void release();
}
