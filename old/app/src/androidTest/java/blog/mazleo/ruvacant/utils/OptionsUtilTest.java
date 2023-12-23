package blog.mazleo.ruvacant.utils;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

@RunWith(AndroidJUnit4.class)
public class OptionsUtilTest {
    @Test
    public void getCurrentSemesterMonthCodeTest() {
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(1)),
                new Integer(1)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(2)),
                new Integer(1)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(3)),
                new Integer(1)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(4)),
                new Integer(1)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(5)),
                new Integer(1)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(6)),
                new Integer(1)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(7)),
                new Integer(7)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(8)),
                new Integer(7)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(9)),
                new Integer(9)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(10)),
                new Integer(9)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(11)),
                new Integer(9)
        );
        Assert.assertEquals(
                new Integer(OptionsUtil.getCurrentSemesterMonthCode(12)),
                new Integer(0)
        );
    }

    @Test
    public void fromSemesterMonthCodeToStringTest() {
        Assert.assertEquals(
                OptionsUtil.fromSemesterMonthCodeToMonthString(1),
                "Spring"
        );
        Assert.assertEquals(
                OptionsUtil.fromSemesterMonthCodeToMonthString(7),
                "Summer"
        );
        Assert.assertEquals(
                OptionsUtil.fromSemesterMonthCodeToMonthString(9),
                "Fall"
        );
        Assert.assertEquals(
                OptionsUtil.fromSemesterMonthCodeToMonthString(0),
                "Winter"
        );
    }
}
