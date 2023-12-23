package blog.mazleo.ruvacant.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Subject.class,
                        parentColumns = {
                                "code"
                        },
                        childColumns = {
                                "subject_code"
                        }
                ),
                @ForeignKey(
                        entity = Course.class,
                        parentColumns = {
                                "subject_code",
                                "code"
                        },
                        childColumns = {
                                "subject_code",
                                "course_code"
                        }
                )
        },
        indices = {
                @Index(
                        value = {
                                "subject_code",
                                "course_code"
                        }
                )
        }
)
public class Class {
    @NonNull
    @PrimaryKey
    private String index;
    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "subject_code", index = true)
    private String subjectCode;
    @ColumnInfo(name = "course_code")
    private String courseCode;

    public Class(String index, String code, String subjectCode, String courseCode) {
        this.index = index;
        this.code = code;
        this.subjectCode = subjectCode;
        this.courseCode = courseCode;
    }

    @Override
    public String toString() {
        return "Class{" +
                "index='" + index + '\'' +
                ", code='" + code + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                ", courseCode='" + courseCode + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Class)) return false;

        Class classToCompare = (Class) obj;

        return index.equals(classToCompare.getIndex());
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
