package blog.mazleo.ruvacant.model;

import androidx.annotation.Nullable;

public class Course {
    private String subjectCode;
    private String code;
    private String title;
    private String expandedTitle;

    public Course(String subjectCode, String code, String title, String expandedTitle) {
        this.subjectCode = subjectCode;
        this.code = code;
        this.title = title;
        this.expandedTitle = expandedTitle;
    }

    @Override
    public String toString() {
        return "Course{" +
                "subjectCode='" + subjectCode + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", expandedTitle='" + expandedTitle + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return (subjectCode + "-" + code).hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Course)) return false;

        Course courseToCompare = (Course) obj;

        return subjectCode.equals(courseToCompare.getSubjectCode()) && code.equals(courseToCompare.getCode());
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpandedTitle() {
        return expandedTitle;
    }

    public void setExpandedTitle(String expandedTitle) {
        this.expandedTitle = expandedTitle;
    }
}
