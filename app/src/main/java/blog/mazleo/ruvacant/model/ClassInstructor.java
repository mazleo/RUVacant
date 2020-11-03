package blog.mazleo.ruvacant.model;

import androidx.annotation.Nullable;

public class ClassInstructor {
    private String classIndex;
    private String instructorLastName;
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
