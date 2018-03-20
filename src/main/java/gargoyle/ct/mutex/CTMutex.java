package gargoyle.ct.mutex;

public interface CTMutex {

    boolean acquire();

    void release();
}
