package gargoyle.ct;

import gargoyle.ct.task.helper.CTTimeHelper;
import gargoyle.ct.task.helper.impl.CTTimeHelperImpl;
import gargoyle.ct.util.CTTimeUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CTTimeHelperImplTest {
    private CTTimeHelper helper;

    @Before
    public void setUp() {
        helper = new CTTimeHelperImpl();
    }

    @After
    public void tearDown() {
        helper = null;
    }

    @Test
    public void testFakeTime() {
        long fakeTime = CTTimeUtil.currentTimeMillis();
        helper.setFakeTime(fakeTime);
        Assert.assertEquals(fakeTime, helper.getFakeTime());
        long expected = CTTimeUtil.currentTimeMillis();
        long actual = helper.currentTimeMillis();
        Assert.assertTrue(expected - actual <= 0);
    }
}
