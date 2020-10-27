package blog.mazleo.ruvacant.utils;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class NamingUtilTest {
    @Test
    public void getSeparatedCapitalizedNameListTest() {
        String input1 = "BELOWICH, ALAN";
        String input2 = "BELOWICH,ALAN";
        String input3 = "INTRO COMPUTERS&APPL";
        String input4 = "INTRO COMPUTER SCI";
        String input5 = "COMP APPS-BUSINESS";

        List<String> result1 = NamingUtil.getSeparatedCapitalizedNameList(input1);
        List<String> result2 = NamingUtil.getSeparatedCapitalizedNameList(input2);
        List<String> result3 = NamingUtil.getSeparatedCapitalizedNameList(input3);
        List<String> result4 = NamingUtil.getSeparatedCapitalizedNameList(input4);
        List<String> result5 = NamingUtil.getSeparatedCapitalizedNameList(input5);

        Assert.assertNotNull(result1);
        Log.i("APPDEBUG", result1.toString());
        Assert.assertTrue(result1.contains("Belowich"));
        Assert.assertTrue(result1.contains(","));
        Assert.assertTrue(result1.contains("Alan"));

        Assert.assertNotNull(result2);
        Log.i("APPDEBUG", result2.toString());
        Assert.assertTrue(result2.contains("Belowich"));
        Assert.assertTrue(result2.contains(","));
        Assert.assertTrue(result2.contains("Alan"));

        Assert.assertNotNull(result3);
        Log.i("APPDEBUG", result3.toString());
        Assert.assertTrue(result3.contains("Intro"));
        Assert.assertTrue(result3.contains("Computers"));
        Assert.assertTrue(result3.contains("&"));
        Assert.assertTrue(result3.contains("Appl"));

        Assert.assertNotNull(result4);
        Log.i("APPDEBUG", result4.toString());
        Assert.assertTrue(result4.contains("Intro"));
        Assert.assertTrue(result4.contains("Computer"));
        Assert.assertTrue(result4.contains("Sci"));

        Assert.assertNotNull(result5);
        Log.i("APPDEBUG", result5.toString());
        Assert.assertTrue(result5.contains("Comp"));
        Assert.assertTrue(result5.contains("Apps"));
        Assert.assertTrue(result5.contains("-"));
        Assert.assertTrue(result5.contains("Business"));
    }
}
