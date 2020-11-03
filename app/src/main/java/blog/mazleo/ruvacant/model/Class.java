package blog.mazleo.ruvacant.model;

import androidx.annotation.Nullable;

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
