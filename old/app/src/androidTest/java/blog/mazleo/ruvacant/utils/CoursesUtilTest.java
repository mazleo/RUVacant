package blog.mazleo.ruvacant.utils;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CoursesUtilTest {
    @Test
    public void parseTimeDataToSecondOfDayTest() {
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("1200", CoursesUtil.TimeCode.AM), 0);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("1227", CoursesUtil.TimeCode.AM), 1620);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("0130", CoursesUtil.TimeCode.AM), 5400);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("0515", CoursesUtil.TimeCode.AM), 18900);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("1145", CoursesUtil.TimeCode.AM), 42300);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("1200", CoursesUtil.TimeCode.PM), 43200);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("1230", CoursesUtil.TimeCode.PM), 45000);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("0230", CoursesUtil.TimeCode.PM), 52200);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("0745", CoursesUtil.TimeCode.PM), 71100);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("1152", CoursesUtil.TimeCode.PM), 85920);
        Assert.assertEquals(CoursesUtil.parseTimeDataToSecondOfDay("1159", CoursesUtil.TimeCode.PM), 86340);
    }
}
