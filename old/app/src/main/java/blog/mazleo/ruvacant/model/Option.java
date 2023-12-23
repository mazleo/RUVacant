package blog.mazleo.ruvacant.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        primaryKeys = {
                "semester_month",
                "semester_year",
                "school_campus_code",
                "level_code"
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Semester.class,
                        parentColumns = {
                                "month",
                                "year"
                        },
                        childColumns = {
                                "semester_month",
                                "semester_year"
                        }
                ),
                @ForeignKey(
                        entity = SchoolCampus.class,
                        parentColumns = {
                                "code"
                        },
                        childColumns = {
                                "school_campus_code"
                        }
                ),
                @ForeignKey(
                        entity = Level.class,
                        parentColumns = {
                                "code"
                        },
                        childColumns = {
                                "level_code"
                        }
                )
        }
)
public class Option {
    @ColumnInfo(name = "semester_month")
    int semesterMonth;
    @ColumnInfo(name = "semester_year")
    int semesterYear;
    @NonNull
    @ColumnInfo(name = "school_campus_code", index = true)
    String schoolCampusCode;
    @NonNull
    @ColumnInfo(name = "level_code", index = true)
    String levelCode;

    public Option(int semesterMonth, int semesterYear, String schoolCampusCode, String levelCode) {
        this.semesterMonth = semesterMonth;
        this.semesterYear = semesterYear;
        this.schoolCampusCode = schoolCampusCode;
        this.levelCode = levelCode;
    }

    public int getSemesterMonth() {
        return semesterMonth;
    }

    public void setSemesterMonth(int semesterMonth) {
        this.semesterMonth = semesterMonth;
    }

    public int getSemesterYear() {
        return semesterYear;
    }

    public void setSemesterYear(int semesterYear) {
        this.semesterYear = semesterYear;
    }

    public String getSchoolCampusCode() {
        return schoolCampusCode;
    }

    public void setSchoolCampusCode(String schoolCampusCode) {
        this.schoolCampusCode = schoolCampusCode;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    @Override
    public String toString() {
        return "Option{" +
                "semesterMonth=" + semesterMonth +
                ", semesterYear=" + semesterYear +
                ", schoolCampusCode='" + schoolCampusCode + '\'' +
                ", levelCode='" + levelCode + '\'' +
                '}';
    }
}
