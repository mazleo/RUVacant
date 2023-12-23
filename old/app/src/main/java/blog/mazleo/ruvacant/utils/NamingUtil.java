package blog.mazleo.ruvacant.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NamingUtil {
    public static List<String> getSeparatedCapitalizedNameList(String fullName) {
        String[] splittedName = fullName.split("((\\s*)(?<=\\p{Punct})(\\s*)|(\\s*)(?=\\p{Punct})(\\s*))|(\\s)");
        List<String> resultNameList = new ArrayList<>();

        for (String perName : splittedName) {
            if (perName.length() > 0) {
                resultNameList.add(
                        perName.substring(0, 1).toUpperCase()
                        + perName.substring(1).toLowerCase()
                );
            }
        }

        return resultNameList;
    }

    public static String joinSplitNameArray(List<String> splitName) {
        String joinedName = "";
        for (int n = 0; n < splitName.size(); n++) {
            joinedName += splitName.get(n);
            if (n != splitName.size() - 1) {
                joinedName += " ";
            }
        }

        return joinedName;
    }
}
