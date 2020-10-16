package blog.mazleo.ruvacant.model;

public class Class {
    private String index;
    private String code;
    private String subjectCode;
    private String courseCode;

    public Class(String index, String code, String subjectCode, String courseCode) {
        this.index = index;
        this.code = code;
        this.subjectCode = subjectCode;
        this.courseCode = courseCode;
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
