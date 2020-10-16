package blog.mazleo.ruvacant.model;

public class ClassInstructor {
    private String classIndex;
    private String instructorLastName;
    private String instructorFirstName;

    public ClassInstructor(String classIndex, String instructorLastName, String instructorFirstName) {
        this.classIndex = classIndex;
        this.instructorLastName = instructorLastName;
        this.instructorFirstName = instructorFirstName;
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
