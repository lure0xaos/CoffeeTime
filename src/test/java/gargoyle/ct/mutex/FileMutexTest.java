package gargoyle.ct.mutex;

import gargoyle.ct.mutex.impl.FileMutex;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileMutexTest {

    @Test
    public void testMutex() {
        CTMutex mutex = new FileMutex(FileMutexTest.class.getSimpleName());
        assertTrue(mutex.acquire());
        assertFalse(mutex.acquire());
        mutex.release();
        assertTrue(mutex.acquire());
        mutex.release();
    }
}
