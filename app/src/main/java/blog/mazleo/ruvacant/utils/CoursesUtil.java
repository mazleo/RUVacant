package blog.mazleo.ruvacant.utils;

import java.time.LocalTime;

import blog.mazleo.ruvacant.service.deserializer.CourseInfoDeserializer;

public class CoursesUtil {
    public static String RUTGERS_SIS_BASE_URL = "http://sis.rutgers.edu";

    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;

    public enum TimeCode {
        AM,
        PM
    }

    public static int parseTimeDataToSecondOfDay(String unparsedTime, TimeCode timeCode) {
        String unparsedHour = unparsedTime.substring(0, 2);
        String unparsedMinute = unparsedTime.substring(2);
        int hour = (
                (timeCode == TimeCode.AM && unparsedHour.equals("12")) ? 0
                : (Integer.parseInt(unparsedHour) + (timeCode == TimeCode.PM && Integer.parseInt(unparsedHour) != 12 ? 12 : 0))
        );
        int minute = Integer.parseInt(unparsedMinute);

        return (hour * MINUTES_PER_HOUR * SECONDS_PER_MINUTE) + (minute * SECONDS_PER_MINUTE);
    }
}
