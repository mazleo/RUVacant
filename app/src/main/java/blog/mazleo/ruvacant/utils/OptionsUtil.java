package blog.mazleo.ruvacant.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OptionsUtil {
    public static List<String> getSemesterOptionsStrings() {
        List<String> semesterOptions = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        int[] semesterMonthCodes = {1, 7, 9, 0};
        int[] yearCodes = {currentYear - 1, currentYear};
        int currentSemesterMonthCode = getCurrentSemesterMonthCode(currentMonth);

        int currentSemesterMonthCodeIndex = getIndexOfIntFromIntArray(semesterMonthCodes, currentSemesterMonthCode);
        int currentYearCodeIndex = 1;

        for (int i = 0; i < 4; i++) {
            semesterOptions.add(
                    fromSemesterMonthCodeToMonthString(semesterMonthCodes[currentSemesterMonthCodeIndex])
                    + " " + yearCodes[currentYearCodeIndex]
            );

            if (currentSemesterMonthCodeIndex == 0) {
                currentSemesterMonthCodeIndex = semesterMonthCodes.length - 1;
                currentYearCodeIndex--;
            }
            else {
                currentSemesterMonthCodeIndex--;
            }
        }

        return semesterOptions;
    }

    public static int getCurrentSemesterMonthCode(int currentMonth) {
        if (currentMonth == 12) {
            return 0;
        }
        else if (currentMonth >= 9) {
            return 9;
        }
        else if (currentMonth >= 7) {
            return 7;
        }
        else if (currentMonth >= 1) {
            return 1;
        }

        return 9;
    }

    public static String fromSemesterMonthCodeToMonthString(int semesterMonthCode) {
        if (semesterMonthCode == 1) {
            return "Spring";
        }
        else if (semesterMonthCode == 7) {
            return "Summer";
        }
        else if (semesterMonthCode == 9) {
            return "Fall";
        }
        else if (semesterMonthCode == 0) {
            return "Winter";
        }

        return "Fall";
    }

    public static int getIndexOfIntFromIntArray(int[] intArr, int target) {
        for (int i = 0; i < intArr.length; i++) {
            if (target == intArr[i]) {
                return i;
            }
        }

        return -1;
    }

    public static List<String> getCampusOptionsStrings() {
        List<String> campusOptions = new ArrayList<>();
        campusOptions.add("New Brunswick");
        campusOptions.add("Newark");
        campusOptions.add("Camden");

        return campusOptions;
    }
}
