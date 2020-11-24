package blog.mazleo.ruvacant.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = SchoolCampus.class,
                        parentColumns = {
                                "code"
                        },
                        childColumns = {
                                "school_campus_code"
                        }
                )
        }
)
public class Campus {
    @NonNull
    @PrimaryKey
    private String code;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "school_campus_code")
    private String schoolCampusCode;

    public Campus(String code, String name, String schoolCampusCode) {
        this.code = code;
        this.name = name;
        this.schoolCampusCode = schoolCampusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolCampusCode() {
        return schoolCampusCode;
    }

    public void setSchoolCampusCode(String schoolCampusCode) {
        this.schoolCampusCode = schoolCampusCode;
    }
}
