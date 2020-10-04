package blog.mazleo.ruvacant.model;

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
