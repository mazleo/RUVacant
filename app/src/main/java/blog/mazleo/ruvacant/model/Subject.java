package blog.mazleo.ruvacant.model;

import androidx.annotation.Nullable;

public class Subject {
    String code;
    String title;

    public Subject(String code, String title) {
        this.code = code;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Subject)) return false;

        Subject subjectToCompare = (Subject) obj;

        return code.equals(subjectToCompare.getCode()) && title.equals(subjectToCompare.getTitle());
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
}
