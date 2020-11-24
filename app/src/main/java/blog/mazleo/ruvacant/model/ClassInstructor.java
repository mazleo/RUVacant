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
                "instructor_last_name",
                "instructor_first_name"
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
                        entity = Instructor.class,
                        parentColumns = {
                                "last_name",
                                "first_name"
                        },
                        childColumns = {
                                "instructor_last_name",
                                "instructor_first_name"
                        }
                )
        },
        indices = {
                @Index(
                        value = {
                                "instructor_last_name",
                                "instructor_first_name"
                        }
                )
        }
)
public class ClassInstructor {
    @NonNull
    @ColumnInfo(name = "class_index")
    private String classIndex;
    @NonNull
    @ColumnInfo(name = "instructor_last_name", index = true)
    private String instructorLastName;
    @NonNull
    @ColumnInfo(name = "instructor_first_name", index = true)
    private String instructorFirstName;

    public ClassInstructor(String classIndex, String instructorLastName, String instructorFirstName) {
        this.classIndex = classIndex;
        this.instructorLastName = instructorLastName;
        this.instructorFirstName = instructorFirstName;
    }

    @Override
    public String toString() {
        return "ClassInstructor{" +
                "classIndex='" + classIndex + '\'' +
                ", instructorLastName='" + instructorLastName + '\'' +
                ", instructorFirstName='" + instructorFirstName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return (classIndex + ":" + instructorLastName + "," + instructorFirstName).hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof ClassInstructor)) return false;

        ClassInstructor classInstructorToCompare = (ClassInstructor) obj;

        return (
                classIndex.equals(classInstructorToCompare.getClassIndex())
                && instructorLastName.equals(classInstructorToCompare.getInstructorLastName())
                && instructorFirstName.equals(classInstructorToCompare.getInstructorFirstName())
                );
    }

    public String getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(String classIndex) {
        this.classIndex = classIndex;
    }

    public String getInstructorLastName() {
        return instructorLastName;
    }

    public void setInstructorLastName(String instructorLastName) {
        this.instructorLastName = instructorLastName;
    }

    public String getInstructorFirstName() {
        return instructorFirstName;
    }

    public void setInstructorFirstName(String instructorFirstName) {
        this.instructorFirstName = instructorFirstName;
    }
}
