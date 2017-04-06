package gargoyle.ct;

import gargoyle.ct.task.helper.CTTimeHelper;
import gargoyle.ct.task.helper.impl.CTTimeHelperImpl;
import gargoyle.ct.util.CTTimeUtil;
import org.junit.Assert;
import org.junit.Test;

public class CTTimeHelperImplTest {
    @Test
    public void testFakeTime() {
        CTTimeHelper helper = new CTTimeHelperImpl();
        long fakeTime = CTTimeUtil.currentTimeMillis();
        helper.setFakeTime(fakeTime);
        Assert.assertEquals(fakeTime, helper.getFakeTime());
        long expected = CTTimeUtil.currentTimeMillis();
        long actual = helper.currentTimeMillis();
        Assert.assertTrue(expected - actual <= 0);
    }
}
