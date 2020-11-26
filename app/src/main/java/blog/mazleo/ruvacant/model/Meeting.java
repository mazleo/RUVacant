package blog.mazleo.ruvacant.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        primaryKeys = {
                "class_index",
                "building_code",
                "room_number",
                "meeting_day",
                "start_time",
                "end_time"
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Class.class,
                        parentColumns = {
                                "index"
                        },
                        childColumns = {
                                "class_index"
                        }
                ),
                @ForeignKey(
                        entity = Building.class,
                        parentColumns = {
                                "code"
                        },
                        childColumns = {
                                "building_code"
                        }
                ),
                @ForeignKey(
                        entity = Room.class,
                        parentColumns = {
                                "building_code",
                                "number"
                        },
                        childColumns = {
                                "building_code",
                                "room_number"
                        }
                )
        },
        indices = {
                @Index(
                        value = {
                                "building_code",
                                "room_number"
                        }
                )
        }
)
public class Meeting {
    @NonNull
    @ColumnInfo(name = "class_index")
    private String classIndex;
    @NonNull
    @ColumnInfo(name = "building_code", index = true)
    private String buildingCode;
    @NonNull
    @ColumnInfo(name = "room_number", index = true)
    private String roomNumber;
    @NonNull
    @ColumnInfo(name = "meeting_day")
    private String meetingDay;
    @ColumnInfo(name = "start_time")
    private int startTime;
    @ColumnInfo(name = "end_time")
    private int endTime;

    public Meeting(String classIndex, String buildingCode, String roomNumber, String meetingDay, int startTime, int endTime) {
        this.classIndex = classIndex;
        this.buildingCode = buildingCode;
        this.roomNumber = roomNumber;
        this.meetingDay = meetingDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        return (
                classIndex + ":"
                + buildingCode + "-" + roomNumber + ":"
                + meetingDay + ":"
                + startTime + "-" + endTime
                ).hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Meeting)) return false;

        Meeting meetingToCompare = (Meeting) obj;

        return (
                classIndex.equals(meetingToCompare.getClassIndex())
                && buildingCode.equals(meetingToCompare.getBuildingCode())
                && roomNumber.equals(meetingToCompare.getRoomNumber())
                && meetingDay.equals(meetingToCompare.getMeetingDay())
                && startTime == meetingToCompare.getStartTime()
                && endTime == meetingToCompare.getEndTime()
                );
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "classIndex='" + classIndex + '\'' +
                ", buildingCode='" + buildingCode + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", meetingDay='" + meetingDay + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public String getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(String classIndex) {
        this.classIndex = classIndex;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getMeetingDay() {
        return meetingDay;
    }

    public void setMeetingDay(String meetingDay) {
        this.meetingDay = meetingDay;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
