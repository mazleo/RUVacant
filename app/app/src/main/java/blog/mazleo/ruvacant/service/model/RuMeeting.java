package blog.mazleo.ruvacant.service.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Calendar;

/** A course meeting. */
@Entity
public final class RuMeeting {

  private static final int HOURS_PER_DAY = 24;
  private static final int MINUTES_PER_HOUR = 60;
  private static final int MINUTES_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR;

  private static int convertToMinuteOfDay(String ruTime, String pmCode) {
    int hourHand = Integer.parseInt(ruTime.substring(/* beginIndex= */ 0, /* endIndex= */ 2));
    int minuteHand = Integer.parseInt(ruTime.substring(/* beginIndex= */ 2));
    int minutesOffset = pmCode.equals("P") ? MINUTES_PER_DAY / 2 : 0;
    return (hourHand * MINUTES_PER_HOUR + minutesOffset) + minuteHand;
  }

  private static int convertRuEndToMinuteOfDay(String ruEnd, int start) {
    int endAm = convertToMinuteOfDay(ruEnd, /* pmCode= */ "A");
    int endPm = convertToMinuteOfDay(ruEnd, /* pmCode= */ "P");
    return start < endAm ? endAm : endPm;
  }

  private static int convertDayOfWeek(String ruMeetingDay) {
    switch (ruMeetingDay) {
      case "M":
        return Calendar.MONDAY;
      case "T":
        return Calendar.TUESDAY;
      case "W":
        return Calendar.WEDNESDAY;
      case "TH":
        return Calendar.THURSDAY;
      case "F":
        return Calendar.FRIDAY;
      case "S":
        return Calendar.SATURDAY;
      case "U":
        return Calendar.SUNDAY;
      default:
        throw new IllegalArgumentException(
            String.format("ruMeetingDay \"%s\" not supported.", ruMeetingDay));
    }
  }

  /** Format: "CAMPUS.BUILDING.ROOM.DAY.START" */
  @PrimaryKey @NonNull public String key;

  /** Minute of the day. */
  public int start;

  /** Minute of the day. */
  public int end;

  /** Calendar.class day of the week. */
  public int dayOfWeek;

  public String sectionCode;
  public String courseKey;
  public String roomCode;
  public String buildingCode;
  public String semesterCode;
  public String uniCampusCode;
  public String levelCode;

  public RuMeeting(
      String key,
      int start,
      int end,
      int dayOfWeek,
      String sectionCode,
      String courseKey,
      String roomCode,
      String buildingCode,
      String semesterCode,
      String uniCampusCode,
      String levelCode) {
    this.key = key;
    this.start = start;
    this.end = end;
    this.dayOfWeek = dayOfWeek;
    this.sectionCode = sectionCode;
    this.courseKey = courseKey;
    this.roomCode = roomCode;
    this.buildingCode = buildingCode;
    this.uniCampusCode = uniCampusCode;
  }

  @Ignore
  public RuMeeting(
      String ruStart,
      String ruEnd,
      String pmCode,
      String ruMeetingDay,
      String sectionCode,
      String courseKey,
      String roomCode,
      String buildingCode,
      String semesterCode,
      String uniCampusCode,
      String levelCode) {
    start = convertToMinuteOfDay(ruStart, pmCode);
    end = convertRuEndToMinuteOfDay(ruEnd, start);
    dayOfWeek = convertDayOfWeek(ruMeetingDay);
    this.sectionCode = sectionCode;
    this.courseKey = courseKey;
    this.roomCode = roomCode;
    this.buildingCode = buildingCode;
    this.semesterCode = semesterCode;
    this.uniCampusCode = uniCampusCode;
    this.levelCode = levelCode;
    key = String.format("%s.%s.%s.%s.%s", uniCampusCode, buildingCode, roomCode, dayOfWeek, start);
  }
}
